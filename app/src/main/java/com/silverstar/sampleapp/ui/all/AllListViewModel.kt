package com.silverstar.sampleapp.ui.all

import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.entity.Item
import com.silverstar.sampleapp.utils.Result
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AllListViewModel(
    mergeTwoItemProcessorHolder: ProcessorHolder<Int, Result<List<Item>>>
) {

    private val _isLoading = BehaviorSubject.createDefault(false)
    val isLoading: Observable<Boolean> = _isLoading

    private val allItemListRequest = BehaviorSubject.create<Int>()

    private val allItemListResult: Observable<Result<List<Item>>> =
        allItemListRequest
            .doOnNext { _isLoading.onNext(true) }
            .compose(mergeTwoItemProcessorHolder.processor)
            .doOnNext { _isLoading.onNext(false) }
            .share()

    val list: Observable<List<Item>> =
        allItemListResult
            .filter { it is Result.OnSuccess }
            .map { it as Result.OnSuccess<List<Item>> }
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
