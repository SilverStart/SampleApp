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

    private val mock: ProcessorHolder<Unit, Result<List<Item>>> = mock()

    private lateinit var viewModel: AllListViewModel

    @Nested
    @DisplayName("init 메소드는")
    inner class DescribeInit {

        @Nested
        @DisplayName("빈 List 를 리턴하는 mock 을 주입한 ViewModel 에서")
        inner class ContextWithMockThatReturnsEmptyList {

            @BeforeEach
            fun prepareMock() {
                whenever(mock.processor).thenReturn(
                    ObservableTransformer {
                        it.flatMap {
                            Observable.just(Result.OnSuccess(emptyList<Item>()))
                        }
                    }
                )
            }

            @Test
            @DisplayName("호출 시 빈 List 를 리턴한다")
            fun itReturnsEmptyMock() {
                viewModel = AllListViewModel(mock)

                val loadingTest = viewModel.isLoading.test()
                val listTest = viewModel.list.test()

                viewModel.init()

                listTest.assertValue(emptyList())
                loadingTest.assertValues(false, true, false)
            }
        }

        @Nested
        @DisplayName("값을 가진 List 를 리턴하는 mock 을 주입한 ViewModel 에서")
        inner class ContextWithMockThatReturnsListThatHasValue {

            private val listThatHasValue =
                listOf(Item("name", "thumbnailUrl", "imageUrl", 10f, false))

            @BeforeEach
            fun prepareMock() {
                whenever(mock.processor).thenReturn(
                    ObservableTransformer {
                        it.flatMap {
                            Observable.just(Result.OnSuccess(listThatHasValue))
                        }
                    }
                )
            }

            @Test
            @DisplayName("호출 시 mock 에서 받은 List 와 동일한 List 를 리턴한다")
            fun itReturnsListSameWithGotFromMock() {
                viewModel = AllListViewModel(mock)

                val loadingTest = viewModel.isLoading.test()
                val listTest = viewModel.list.test()

                viewModel.init()

                listTest.assertValue(listThatHasValue)
                loadingTest.assertValues(false, true, false)
            }
        }
    }
}