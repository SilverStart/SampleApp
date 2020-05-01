package com.silverstar.sampleapp.business

import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.pojo.ItemFromServer
import com.silverstar.sampleapp.data.service.ItemService
import com.silverstar.sampleapp.utils.Result
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class LoadItemProcessorHolder @Inject constructor(itemService: ItemService) :
    ProcessorHolder<Int, Result<List<ItemFromServer>>> {
    override val processor: ObservableTransformer<Int, Result<List<ItemFromServer>>>
        get() = TODO("Not yet implemented")
}