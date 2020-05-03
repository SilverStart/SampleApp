package com.silverstar.sampleapp.ui.liked

import com.silverstar.sampleapp.business.SortOption
import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.entity.Item
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class LikedListViewModel(
    loadLikedItemProcessorHolder: ProcessorHolder<SortOption, List<Item>>
) {
    private val likedItemListRequest = BehaviorSubject.create<SortOption>()

    val list: Observable<List<Item>> =
        likedItemListRequest
            .compose(loadLikedItemProcessorHolder.processor)

    fun init() {
        sortLikedItemWith(SortOption.RATE_DESCENDING)
    }

    fun sortLikedItemWith(sortOption: SortOption) {
        likedItemListRequest.onNext(sortOption)
    }
}
