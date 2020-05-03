package com.silverstar.sampleapp.ui.liked

import com.silverstar.sampleapp.business.InsertOrDeleteByLikedProcessorHolder
import com.silverstar.sampleapp.business.LoadLikedItemProcessorHolder
import com.silverstar.sampleapp.ui.base.ItemUpdateViewModel
import javax.inject.Inject

class LikedListHoldViewModel @Inject constructor(
    loadLikedItemProcessorHolder: LoadLikedItemProcessorHolder,
    insertOrDeleteByLikedProcessorHolder: InsertOrDeleteByLikedProcessorHolder
) : ItemUpdateViewModel(insertOrDeleteByLikedProcessorHolder) {

    val likedListViewModel = LikedListViewModel(loadLikedItemProcessorHolder)

    fun init() {
        likedListViewModel.init()
    }
}