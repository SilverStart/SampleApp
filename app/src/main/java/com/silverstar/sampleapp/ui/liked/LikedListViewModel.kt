package com.silverstar.sampleapp.ui.liked

import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.entity.Item
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class LikedListViewModel(
    loadLikedItemProcessorHolder: ProcessorHolder<Unit, List<Item>>
) {
    private val likedItemListRequest = BehaviorSubject.create<Unit>()

    val list: Observable<List<Item>> =
        likedItemListRequest
            .compose(loadLikedItemProcessorHolder.processor)

    fun init() {
        likedItemListRequest.onNext(Unit)
    }
}
