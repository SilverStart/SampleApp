package com.silverstar.sampleapp.business

import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.entity.Item
import com.silverstar.sampleapp.data.pojo.ItemFromServer
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class MergeTwoItemProcessorHolder @Inject constructor() :
    ProcessorHolder<Pair<List<ItemFromServer>, List<Item>>, List<Item>> {
    override val processor: ObservableTransformer<Pair<List<ItemFromServer>, List<Item>>, List<Item>>
        get() = TODO("Not yet implemented")
}