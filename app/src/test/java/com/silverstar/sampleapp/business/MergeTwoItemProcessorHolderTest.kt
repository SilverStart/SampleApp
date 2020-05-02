package com.silverstar.sampleapp.business

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.dao.ItemDao
import com.silverstar.sampleapp.data.entity.Item
import com.silverstar.sampleapp.data.pojo.Description
import com.silverstar.sampleapp.data.pojo.ItemFromServer
import com.silverstar.sampleapp.utils.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("MergeTwoItemProcessorHolder 클래스")
class MergeTwoItemProcessorHolderTest {

    private val loadItemByPageProcessorHolder: ProcessorHolder<Int, Result<List<ItemFromServer>>> =
        mock()

    private val itemDao: ItemDao = mock()

    private lateinit var mergeTwoItemProcessorHolder: MergeTwoItemProcessorHolder

    private fun getEmptyItemFromServerList(): List<ItemFromServer> = emptyList()

    private fun getEmptyItemList(): List<Item> = emptyList()

    private fun getItemListThatHasNItems(n: Int): List<Item> {
        val listThatHasNItems = ArrayList<Item>()

        repeat(n) {
            listThatHasNItems.add(
                Item("name", "thumbnailUrl", "imageUrl", "subject", 1000, 10f, false)
            )
        }

        return listThatHasNItems
    }

    @Nested
    @DisplayName("빈 List 를 리턴하는 LoadItemByPageProcessorHolder 와")
    inner class ContextWithLoadItemByPageProcessorHolderThatReturnsEmptyList {

        private val emptyItemFromServerList: List<ItemFromServer> = getEmptyItemFromServerList()

        @BeforeEach
        fun prepareMock() {
            whenever(loadItemByPageProcessorHolder.processor)
                .thenReturn(
                    ObservableTransformer {
                        it.flatMap { Observable.just(Result.OnSuccess(emptyItemFromServerList)) }
                    }
                )
        }

        @Nested
        @DisplayName("빈 List 를 리턴하는 ItemDao 를 주입 시")
        inner class ContextWithItemDaoThatReturnsEmptyList {

            private val emptyItemList: List<Item> = getEmptyItemList()

            @BeforeEach
            fun prepareMock() {
                whenever(itemDao.getAll())
                    .thenReturn(Observable.just(emptyItemList))
            }

            @Test
            @DisplayName("데이터가 흘러올 경우 빈 List 를 리턴한다")
            fun itReturnsEmptyList() {
                mergeTwoItemProcessorHolder = MergeTwoItemProcessorHolder(
                    loadItemByPageProcessorHolder,
                    itemDao
                )

                Observable.just(1)
                    .compose(mergeTwoItemProcessorHolder.processor)
                    .test()
                    .assertValue(Result.OnSuccess(emptyItemList))
            }
        }

        @Nested
        @DisplayName("1개의 아이템을 가진 List 를 리턴하는 ItemDao 를 주입 시")
        inner class ContextWithItemDaoThatReturnsListThatHas1Item {

            private val emptyItemList: List<Item> = getEmptyItemList()

            private val listThatHas1Item: List<Item> = getItemListThatHasNItems(1)

            @BeforeEach
            fun prepareMock() {
                whenever(itemDao.getAll())
                    .thenReturn(Observable.just(listThatHas1Item))
            }

            @Test
            @DisplayName("데이터가 흘러올 경우 빈 List 를 리턴한다")
            fun itReturnsEmptyList() {
                mergeTwoItemProcessorHolder = MergeTwoItemProcessorHolder(
                    loadItemByPageProcessorHolder,
                    itemDao
                )

                Observable.just(1)
                    .compose(mergeTwoItemProcessorHolder.processor)
                    .test()
                    .assertValue(Result.OnSuccess(emptyItemList))
            }
        }
    }

    @Nested
    @DisplayName("3개의 아이템을 가진 List 를 리턴하는 LoadItemByPageProcessorHolder 와")
    inner class ContextWithLoadItemByPageProcessorHolderThatReturnsListThatHas3Items {

        private val itemFromServerListThatHas3Items: List<ItemFromServer> =
            listOf(
                ItemFromServer(
                    "name",
                    "thumbnailUrl",
                    Description("imageUrl", "subject", 1000),
                    10f
                ),
                ItemFromServer(
                    "name2",
                    "thumbnailUrl",
                    Description("imageUrl", "subject", 1000),
                    8f
                ),

                ItemFromServer(
                    "name3",
                    "thumbnailUrl",
                    Description("imageUrl", "subject", 1000),
                    9f
                )
            )

        @BeforeEach
        fun prepareMock() {
            whenever(loadItemByPageProcessorHolder.processor)
                .thenReturn(
                    ObservableTransformer {
                        it.flatMap {
                            Observable.just(Result.OnSuccess(itemFromServerListThatHas3Items))
                        }
                    }
                )
        }

        @Nested
        @DisplayName("빈 List 를 리턴하는 ItemDao 를 주입 시")
        inner class ContextWithItemDaoThatReturnsEmptyList {
            private val emptyItemList: List<Item> = getEmptyItemList()

            private val itemListThatHas3Items: List<Item> = listOf(
                Item("name", "thumbnailUrl", "imageUrl", "subject", 1000, 10f, false),
                Item("name2", "thumbnailUrl", "imageUrl", "subject", 1000, 8f, false),
                Item("name3", "thumbnailUrl", "imageUrl", "subject", 1000, 9f, false)
            )

            @BeforeEach
            fun prepareMock() {
                whenever(itemDao.getAll())
                    .thenReturn(Observable.just(emptyItemList))
            }

            @Test
            @DisplayName("데이터가 흘러들어올 경우 3개의 아이템을 가진 List 를 리턴한다")
            fun itReturnsListThatHas3Items() {
                mergeTwoItemProcessorHolder = MergeTwoItemProcessorHolder(
                    loadItemByPageProcessorHolder,
                    itemDao
                )

                Observable.just(1)
                    .compose(mergeTwoItemProcessorHolder.processor)
                    .test()
                    .assertValue(Result.OnSuccess(itemListThatHas3Items))
            }
        }

        @Nested
        @DisplayName("1개의 아이템을 가진 List 를 리턴하는 ItemDao 를 주입 시")
        inner class ContextWithItemDaoThatReturnsListThatHas1Item {

            private val listThatHas1Item: List<Item> = listOf(
                Item("name", "thumbnailUrl", "imageUrl", "subject", 1000, 10f, true)
            )

            private val itemListWhichIsCompared: List<Item> = listOf(
                Item("name", "thumbnailUrl", "imageUrl", "subject", 1000, 10f, true),
                Item("name2", "thumbnailUrl", "imageUrl", "subject", 1000, 8f, false),
                Item("name3", "thumbnailUrl", "imageUrl", "subject", 1000, 9f, false)
            )


            @BeforeEach
            fun prepareMock() {
                whenever(itemDao.getAll())
                    .thenReturn(Observable.just(listThatHas1Item))
            }

            @Test
            @DisplayName("데이터가 흘러올 경우 ItemDao 에서 리턴한 List 와 비교 후 치환하여 리턴한다")
            fun itReturnsListWhichIsComparedWithListReturnedFromItemDao() {
                mergeTwoItemProcessorHolder = MergeTwoItemProcessorHolder(
                    loadItemByPageProcessorHolder,
                    itemDao
                )

                Observable.just(1)
                    .compose(mergeTwoItemProcessorHolder.processor)
                    .test()
                    .assertValue(Result.OnSuccess(itemListWhichIsCompared))
            }
        }
    }

}