package com.silverstar.sampleapp.ui.all

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.silverstar.sampleapp.business.LoadItemProcessorHolder
import com.silverstar.sampleapp.business.MergeTwoItemProcessorHolder
import com.silverstar.sampleapp.data.dao.ItemDao
import com.silverstar.sampleapp.data.pojo.Description
import com.silverstar.sampleapp.data.pojo.ItemFromServer
import com.silverstar.sampleapp.data.service.ItemService
import com.silverstar.sampleapp.utils.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("AllListViewModel 클래스")
class AllListViewModelTest {

    private val itemService: ItemService = mock()

    private val loadItemProcessorHolder = LoadItemProcessorHolder(itemService)

    private val dao: ItemDao = mock()

    private val mergeTwoItemProcessorHolder = MergeTwoItemProcessorHolder()

    private lateinit var viewModel: AllListViewModel

    @Nested
    @DisplayName("init 메소드는")
    inner class DescribeInit {

        @Nested
        @DisplayName("빈 List 를 리턴하는 loadItemProcessorHolder 을 주입한 ViewModel 에서")
        inner class ContextWithLoadItemProcessorHolderThatReturnsEmptyList {

            @BeforeEach
            fun prepareMock() {
                whenever(itemService.getItemBy(any())).thenReturn(Observable.just(
                    ItemService.ItemResult(ItemService.ItemResult.DataResult(0, emptyList<ItemFromServer>()))
                ))
            }

            @Test
            @DisplayName("호출 시 빈 List 를 리턴한다")
            fun itReturnsEmptyMock() {
                viewModel =
                    AllListViewModel(loadItemProcessorHolder, dao, mergeTwoItemProcessorHolder)

                val loadingTest = viewModel.isLoading.test()
                val listTest = viewModel.list.test()

                viewModel.init()

                listTest.assertValue(emptyList())
                loadingTest.assertValues(false, true, false)
            }
        }

        inner class ContextWith

        @Nested
        @DisplayName("값을 가진 List 를 리턴하는 loadItemProcessorHolder 을 주입한 ViewModel 에서")
        inner class ContextWithLoadItemProcessorHolderThatReturnsListThatHasValue {

            private val listThatHasValue =
                listOf(ItemFromServer("name", "thumbnailUrl", Description("imageUrl"), 10f))

            @BeforeEach
            fun prepareMock() {
                whenever(itemService.getItemBy(any())).thenReturn(Observable.just(
                    ItemService.ItemResult(ItemService.ItemResult.DataResult(1,
                    listThatHasValue))
                ))
//                whenever(loadItemProcessorHolder.processor).thenReturn(
//                    ObservableTransformer {
//                        it.flatMap {
//                            Observable.just(Result.OnSuccess(listThatHasValue))
//                        }
//                    }
//                )
            }

            @Test
            @DisplayName("호출 시 mock 에서 받은 List 와 동일한 List 를 리턴한다")
            fun itReturnsListSameWithGotFromMock() {
                viewModel =
                    AllListViewModel(loadItemProcessorHolder, dao, mergeTwoItemProcessorHolder)

                val loadingTest = viewModel.isLoading.test()
                val listTest = viewModel.list.test()

                viewModel.init()

                listTest.assertValue(listThatHasValue)
                loadingTest.assertValues(false, true, false)
            }
        }
    }
}