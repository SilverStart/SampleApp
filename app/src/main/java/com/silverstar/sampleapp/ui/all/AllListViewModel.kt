package com.silverstar.sampleapp.ui.all

import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.entity.Item
import com.silverstar.sampleapp.utils.Result
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AllListViewModel constructor(processorHolder: ProcessorHolder<Unit, Result<List<Item>>>) {

    private val _isLoading = BehaviorSubject.createDefault(false)
    val isLoading: Observable<Boolean> = _isLoading

    private val allItemListRequest = BehaviorSubject.create<Unit>()

    private val allItemListResult: Observable<Result<List<Item>>> =
        allItemListRequest
            .doOnNext { _isLoading.onNext(true) }
            .compose(processorHolder.processor)
            .doOnNext { _isLoading.onNext(false) }
            .share()

    val list: Observable<List<Item>> =
        allItemListResult
            .filter { it is Result.OnSuccess }
            .map { it as Result.OnSuccess<List<Item>> }
            .map {
                it.data
            }

    fun init() {
        allItemListRequest.onNext(Unit)
    }

}
