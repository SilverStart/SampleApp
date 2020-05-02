package com.silverstar.sampleapp.business

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.silverstar.sampleapp.data.pojo.Description
import com.silverstar.sampleapp.data.pojo.ItemFromServer
import com.silverstar.sampleapp.data.service.ItemService
import com.silverstar.sampleapp.utils.Result
import io.reactivex.Observable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("LoadItemByPageProcessorHolder 클래스")
class LoadItemByPageProcessorHolderTest {

    private val itemService: ItemService = mock()

    private lateinit var processorHolder: LoadItemByPageProcessorHolder

    private fun getEmptyList(): List<ItemFromServer> = emptyList()

    private fun getListThatHasNItems(n: Int): List<ItemFromServer> {
        val listThatHasNItems = ArrayList<ItemFromServer>()

        repeat(n) {
            listThatHasNItems.add(
                ItemFromServer(
                    "name",
                    "thumbnailUrl",
                    Description("imageUrl"),
                    10f
                )
            )
        }

        return listThatHasNItems
    }

    @Nested
    @DisplayName("빈 List 를 리턴하는 ItemService 를 주입 시")
    inner class ContextWithItemServiceThatReturnsEmptyList {

        @BeforeEach
        fun prepareMock() {
            whenever(itemService.getItemBy(any()))
                .thenReturn(
                    Observable.just(
                        ItemService.ItemResult(
                            ItemService.ItemResult.DataResult(0, getEmptyList())
                        )
                    )
                )
        }

        @Test
        @DisplayName("데이터가 흘러들어오면 빈 List 를 리턴한다")
        fun itReturnsEmptyList() {
            processorHolder = LoadItemByPageProcessorHolder(itemService)

            Observable.just(1)
                .compose(processorHolder.processor)
                .test()
                .assertValue(Result.OnSuccess(getEmptyList()))
        }
    }

    @Nested
    @DisplayName("18개의 아이템을 가진 List 를 리턴하는 ItemService 를 주입 시")
    inner class ContextWithItemServiceThatReturnsListThatHas18Items {

        private val listThatHas18Items: List<ItemFromServer> = getListThatHasNItems(18)

        @BeforeEach
        fun prepareMock() {
            whenever(itemService.getItemBy(any())).thenReturn(
                Observable.just(
                    ItemService.ItemResult(
                        ItemService.ItemResult.DataResult(18, listThatHas18Items)
                    )
                )
            )
        }

        @Test
        @DisplayName("데이터가 흘러들어오면 18개의 아이템을 가진 List 를 리턴한다")
        fun itReturnsListThatHas18Items() {
            processorHolder = LoadItemByPageProcessorHolder(itemService)
            Observable.just(1)
                .compose(processorHolder.processor)
                .test()
                .assertValue(
                    Result.OnSuccess(listThatHas18Items)
                )
        }
    }

    @Nested
    @DisplayName("21개의 아이템을 가진 List 를 리턴하는 ItemService 를 주입 시")
    inner class ContextWithItemServiceThatReturnsListThatHasMoreThan20Items {

        private val listThatHas21Items: List<ItemFromServer> = getListThatHasNItems(21)

        private val listThatHas20Items: List<ItemFromServer> = getListThatHasNItems(20)

        @BeforeEach
        fun prepareMock() {
            whenever(itemService.getItemBy(1)).thenReturn(
                Observable.just(
                    ItemService.ItemResult(
                        ItemService.ItemResult.DataResult(21, listThatHas21Items)
                    )
                )
            )
            whenever(itemService.getItemBy(2)).thenReturn(
                Observable.just(
                    ItemService.ItemResult(
                        ItemService.ItemResult.DataResult(0, getEmptyList())
                    )
                )
            )
        }

        @Test
        @DisplayName("데이터로 1, 2 가 흘러들어오면 20개, 21개의 데이터를 가진 List 를 리턴한다")
        fun itReturnsListsThatHas20ItemsAnd21ItemsWhenComeValue1And2FromSource() {
            processorHolder = LoadItemByPageProcessorHolder(itemService)

            Observable.just(1, 2)
                .compose(processorHolder.processor)
                .test()
                .assertValues(
                    Result.OnSuccess(listThatHas20Items),
                    Result.OnSuccess(listThatHas21Items)
                )
        }
    }
}