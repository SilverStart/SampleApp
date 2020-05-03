package com.silverstar.sampleapp.business

import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.dao.ItemDao
import com.silverstar.sampleapp.data.entity.Item
import com.silverstar.sampleapp.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import java.util.*
import javax.inject.Inject

class InsertOrDeleteByLikedProcessorHolder @Inject constructor(
    private val itemDao: ItemDao,
    private val schedulerProvider: SchedulerProvider
) : ProcessorHolder<Item, Unit> {
    override val processor: ObservableTransformer<Item, Unit>
        get() = ObservableTransformer {
            it.concatMap { item ->
                if (item.liked) delete(item)
                else insert(item.copy(dateTime = Calendar.getInstance().timeInMillis))
            }
                .observeOn(schedulerProvider.ui())
        }

    private fun insert(item: Item): Observable<Unit> {
        return Observable.fromCallable {
            itemDao.insert(item.copy(liked = true))
        }.subscribeOn(schedulerProvider.io())
    }

    private fun delete(item: Item): Observable<Unit> {
        return Observable.fromCallable {
            itemDao.delete(item)
        }.subscribeOn(schedulerProvider.io())
    }
}