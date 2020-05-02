package com.silverstar.sampleapp.business

import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.dao.ItemDao
import com.silverstar.sampleapp.data.entity.Item
import com.silverstar.sampleapp.data.pojo.ItemFromServer
import com.silverstar.sampleapp.utils.Error
import com.silverstar.sampleapp.utils.Result
import com.silverstar.sampleapp.utils.toResult
import io.reactivex.ObservableTransformer
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject

class MergeTwoItemProcessorHolder @Inject constructor(
    loadItemByPageProcessorHolder: ProcessorHolder<Int, Result<List<ItemFromServer>>>,
    itemDao: ItemDao
) :
    ProcessorHolder<Int, Result<List<Item>>> {

    override val processor = ObservableTransformer<Int, Result<List<Item>>> { observable ->
        Observables.combineLatest(
            observable.compose(loadItemByPageProcessorHolder.processor),
            itemDao.getAll()
        ) { a, b ->
            if (a is Result.OnError) error(a.error)
            else compare((a as Result.OnSuccess<List<ItemFromServer>>).data, b)
        }
    }

    private fun error(error: Error): Result<List<Item>> = error.toResult()

    private fun compare(targetList: List<ItemFromServer>, comparedList: List<Item>)
            : Result<List<Item>> {
        return targetList.map {
            val liked = comparedList.find { compared -> it.isEquals(compared) } != null
            Item(it.name, it.thumbnailUrl, it.description.imageUrl, it.rate, liked)
        }.toResult()
    }

    private fun ItemFromServer.isEquals(compared: Item): Boolean {
        return name == compared.name &&
                thumbnailUrl == compared.thumbnailUrl &&
                description.imageUrl == compared.imageUrl &&
                rate == compared.rate
    }

}