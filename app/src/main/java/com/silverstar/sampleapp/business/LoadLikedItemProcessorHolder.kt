package com.silverstar.sampleapp.business

import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.dao.ItemDao
import com.silverstar.sampleapp.data.entity.Item
import com.silverstar.sampleapp.rx.SchedulerProvider
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class LoadLikedItemProcessorHolder @Inject constructor(
    private val itemDao: ItemDao,
    private val schedulerProvider: SchedulerProvider
) : ProcessorHolder<Unit, List<Item>> {
    override val processor: ObservableTransformer<Unit, List<Item>>
        get() = ObservableTransformer {
            it
                .switchMap { itemDao.getAll().subscribeOn(schedulerProvider.io()) }
                .observeOn(schedulerProvider.ui())
        }
}