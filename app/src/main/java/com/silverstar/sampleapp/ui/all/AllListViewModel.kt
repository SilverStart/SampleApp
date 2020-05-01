package com.silverstar.sampleapp.ui.all

import com.silverstar.sampleapp.business.LoadItemProcessorHolder
import com.silverstar.sampleapp.business.MergeTwoItemProcessorHolder
import com.silverstar.sampleapp.data.dao.ItemDao
import com.silverstar.sampleapp.data.pojo.ItemFromServer
import com.silverstar.sampleapp.utils.Result
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AllListViewModel(
    loadItemProcessorHolder: LoadItemProcessorHolder,
    dao: ItemDao,
    mergeTwoItemProcessorHolder: MergeTwoItemProcessorHolder
) {

    private val _isLoading = BehaviorSubject.createDefault(false)
    val isLoading: Observable<Boolean> = _isLoading

    private val allItemListRequest = BehaviorSubject.create<Int>()

    private val allItemListResult: Observable<Result<List<ItemFromServer>>> =
        allItemListRequest
            .doOnNext { _isLoading.onNext(true) }
            .compose(loadItemProcessorHolder.processor)
            .doOnNext { _isLoading.onNext(false) }
            .share()

    val list: Observable<List<ItemFromServer>> =
        allItemListResult
            .filter { it is Result.OnSuccess }
            .map { it as Result.OnSuccess<List<ItemFromServer>> }
            .map {
                it.data
            }

    private var currentPageIndex: Int = INITIAL_PAGE_INDEX

    fun init() {
        currentPageIndex = INITIAL_PAGE_INDEX
        loadNextPage()
    }

    fun loadNextPage() {
        currentPageIndex++
        allItemListRequest.onNext(currentPageIndex)
    }

    companion object {
        private const val INITIAL_PAGE_INDEX = 0
    }
}
