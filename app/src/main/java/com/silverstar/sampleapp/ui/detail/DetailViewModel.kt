package com.silverstar.sampleapp.ui.detail

import com.silverstar.sampleapp.business.InsertOrDeleteByLikedProcessorHolder
import com.silverstar.sampleapp.ui.base.ItemUpdateViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    insertOrDeleteByLikedProcessorHolder: InsertOrDeleteByLikedProcessorHolder
) : ItemUpdateViewModel(insertOrDeleteByLikedProcessorHolder)