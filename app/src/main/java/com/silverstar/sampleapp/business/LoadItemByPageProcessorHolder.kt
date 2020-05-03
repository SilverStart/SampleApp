package com.silverstar.sampleapp.business

import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.pojo.ItemFromServer
import com.silverstar.sampleapp.data.service.ItemService
import com.silverstar.sampleapp.rx.SchedulerProvider
import com.silverstar.sampleapp.utils.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class LoadItemByPageProcessorHolder @Inject constructor(
    private val itemService: ItemService,
    private val schedulerProvider: SchedulerProvider
) :
    ProcessorHolder<Int, @JvmSuppressWildcards Result<List<ItemFromServer>>> {

    private var currentServerPageIndex: Int = INITIAL_INDEX_OF_SERVER_PAGE

    private var currentEndIndexOfCurrentPage: Int = 0

    private val cachedItems: ArrayList<ItemFromServer> = arrayListOf()


    override val processor: ObservableTransformer<Int, Result<List<ItemFromServer>>>
        get() = ObservableTransformer { observable ->
            observable
                .observeOn(schedulerProvider.io())
                .flatMap { pageNumber ->
                    if (cachedItems.size < pageNumber * SIZE_OF_ONE_PAGE) {
                        loadMore()
                    } else {
                        doNothing()
                    }
                }
                .doOnNext { append(it) }
                .map { Result.OnSuccess(getDividedListByPage()) as Result<List<ItemFromServer>> }
                .replay(1)
                .autoConnect()
                .observeOn(schedulerProvider.ui())
        }

    private fun loadMore(): Observable<List<ItemFromServer>> {
        return itemService.getItemBy(currentServerPageIndex++)
            .map { it.data?.product ?: emptyList() }
            .onErrorReturn { emptyList() }
    }

    private fun doNothing(): Observable<List<ItemFromServer>> {
        return Observable.just(emptyList())
    }

    private fun append(itemList: List<ItemFromServer>) {
        cachedItems.addAll(itemList)
    }

    private fun getDividedListByPage(): List<ItemFromServer> {
        currentEndIndexOfCurrentPage += SIZE_OF_ONE_PAGE
        if (cachedItems.size < currentEndIndexOfCurrentPage)
            currentEndIndexOfCurrentPage = cachedItems.size

        return cachedItems.slice(0 until currentEndIndexOfCurrentPage)
    }

    companion object {
        private const val INITIAL_INDEX_OF_SERVER_PAGE = 1
        private const val SIZE_OF_ONE_PAGE = 20
    }
}