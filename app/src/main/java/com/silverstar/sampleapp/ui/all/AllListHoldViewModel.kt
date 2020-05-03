package com.silverstar.sampleapp.ui.all

import com.silverstar.sampleapp.business.InsertOrDeleteByLikedProcessorHolder
import com.silverstar.sampleapp.business.MergeTwoItemProcessorHolder
import com.silverstar.sampleapp.data.entity.Item
import com.silverstar.sampleapp.ui.base.ItemUpdateViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AllListHoldViewModel @Inject constructor(
    mergeTwoItemProcessorHolder: MergeTwoItemProcessorHolder,
    insertOrDeleteByLikedProcessorHolder: InsertOrDeleteByLikedProcessorHolder
) : ItemUpdateViewModel(insertOrDeleteByLikedProcessorHolder) {

    val allListViewModel = AllListViewModel(mergeTwoItemProcessorHolder)
}