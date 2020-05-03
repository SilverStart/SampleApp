package com.silverstar.sampleapp.business

import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.dao.ItemDao
import com.silverstar.sampleapp.data.entity.Item
import com.silverstar.sampleapp.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class LoadLikedItemProcessorHolder @Inject constructor(
    private val itemDao: ItemDao,
    private val schedulerProvider: SchedulerProvider
) : ProcessorHolder<SortOption, List<Item>> {
    override val processor: ObservableTransformer<SortOption, List<Item>>
        get() = ObservableTransformer { observable ->
            observable
                .switchMap { sortOption ->
                    itemDao
                        .getAll()
                        .switchMap { list ->
                            Observable.fromIterable(list)
                                .sorted(sortOption.comparator)
                                .toList()
                                .toObservable()
                        }
                        .subscribeOn(schedulerProvider.io())
                }
                .observeOn(schedulerProvider.ui())
        }
}

enum class SortOption(val comparator: java.util.Comparator<Item>) {
    TIME_ASCENDING(Comparator { o1, o2 -> if (o2.dateTime < o1.dateTime) 1 else -1 }),
    TIME_DESCENDING(Comparator { o1, o2 -> if (o1.dateTime < o2.dateTime) 1 else -1 }),
    RATE_ASCENDING(Comparator { o1, o2 -> if (o2.rate < o1.rate) 1 else -1 }),
    RATE_DESCENDING(Comparator { o1, o2 -> if (o1.rate < o2.rate) 1 else -1 })
}