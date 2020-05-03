package com.silverstar.sampleapp.ui.all

import androidx.lifecycle.ViewModel
import com.silverstar.sampleapp.business.MergeTwoItemProcessorHolder
import javax.inject.Inject

class AllListHoldViewModel @Inject constructor(
    mergeTwoItemProcessorHolder: MergeTwoItemProcessorHolder
) : ViewModel() {

    val allListViewModel = AllListViewModel(mergeTwoItemProcessorHolder)

}