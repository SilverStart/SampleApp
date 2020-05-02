package com.silverstar.sampleapp.ui.all

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.entity.Item
import com.silverstar.sampleapp.utils.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("AllListViewModel 클래스")
class AllListViewModelTest {

    private val mergeTwoItemProcessorHolder: ProcessorHolder<Int, Result<List<Item>>> = mock()

    private lateinit var viewModel: AllListViewModel

    @Nested
    @DisplayName("init 메소드는")
    inner class DescribeInit {

        @Nested
        @DisplayName("빈 List 를 리턴하는 mergeTwoItemProcessorHolder 을 주입한 ViewModel 에서")
        inner class ContextWithLoadItemByPageProcessorHolderThatReturnsEmptyList {

            @BeforeEach
            fun prepareMock() {
                whenever(mergeTwoItemProcessorHolder.processor).thenReturn(
                    ObservableTransformer {
                        it.flatMap {
                            Observable.just(Result.OnSuccess(emptyList<Item>()))
                        }
                    }
                )
            }

            @Test
            @DisplayName("호출 시 빈 List 를 리턴한다")
            fun itReturnsEmptyList() {
                viewModel =
                    AllListViewModel(mergeTwoItemProcessorHolder)

                val loadingTest = viewModel.isLoading.test()
                val listTest = viewModel.list.test()

                viewModel.init()

                listTest.assertValue(emptyList())
                loadingTest.assertValues(false, true, false)
            }
        }

        @Nested
        @DisplayName("값을 가진 List 를 리턴하는 mergeTwoItemProcessorHolder 을 주입한 ViewModel 에서")
        inner class ContextWithLoadItemByPageProcessorHolderThatReturnsListThatHasValue {

            private val listThatHasValue =
                listOf(
                    Item("name", "thumbnailUrl", "imageUrl", 10f, false)
                )

            @BeforeEach
            fun prepareMock() {
                whenever(mergeTwoItemProcessorHolder.processor).thenReturn(
                    ObservableTransformer {
                        it.flatMap {
                            Observable.just(Result.OnSuccess(listThatHasValue))
                        }
                    }
                )
            }

            @Test
            @DisplayName("호출 시 ItemService 에서 받은 List 와 동일한 List 를 리턴한다")
            fun itReturnsListSameWithGotFromItemService() {
                viewModel =
                    AllListViewModel(mergeTwoItemProcessorHolder)

                val loadingTest = viewModel.isLoading.test()
                val listTest = viewModel.list.test()

                viewModel.init()

                listTest.assertValue(listThatHasValue)
                loadingTest.assertValues(false, true, false)
            }
        }
    }
}