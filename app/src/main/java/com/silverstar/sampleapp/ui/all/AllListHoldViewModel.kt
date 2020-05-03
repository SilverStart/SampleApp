package com.silverstar.sampleapp.ui.all

import androidx.lifecycle.ViewModel
import com.silverstar.sampleapp.business.InsertOrDeleteByLikedProcessorHolder
import com.silverstar.sampleapp.business.MergeTwoItemProcessorHolder
import com.silverstar.sampleapp.data.entity.Item
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AllListHoldViewModel @Inject constructor(
    mergeTwoItemProcessorHolder: MergeTwoItemProcessorHolder,
    insertOrDeleteByLikedProcessorHolder: InsertOrDeleteByLikedProcessorHolder
) : ViewModel() {

    val allListViewModel = AllListViewModel(mergeTwoItemProcessorHolder)

    private val _requestUpdatingLikedState = PublishSubject.create<Item>()

    private val compositeDisposable = CompositeDisposable()

    private val isUpdatedLikedState: Observable<Unit> =
        _requestUpdatingLikedState
            .compose(insertOrDeleteByLikedProcessorHolder.processor)

    init {
        compositeDisposable.add(
            isUpdatedLikedState.subscribe {
                // do nothing. for only behavior.
            }
        )
    }

    fun updateLikedState(item: Item) {
        _requestUpdatingLikedState.onNext(item)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}