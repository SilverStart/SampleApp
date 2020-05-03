package com.silverstar.sampleapp.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.silverstar.sampleapp.R
import com.silverstar.sampleapp.ui.adapter.ItemAdapter
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AllListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: AllListHoldViewModel

    private val compositeDisposable = CompositeDisposable()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View

    private val adapter = ItemAdapter {
        viewModel.updateLikedState(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AllListHoldViewModel::class.java)

        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)

        initRecyclerView()
        subscribe()
        viewModel.allListViewModel.init()
    }

    override fun onDestroyView() {
        compositeDisposable.dispose()
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = adapter.itemCount

                if (lastVisibleItemPosition == itemTotalCount - 1) {
                    viewModel.allListViewModel.loadNextPage()
                }
            }
        })
    }

    private fun subscribe() {
        compositeDisposable.add(
            viewModel.allListViewModel
                .list.subscribe {
                    adapter.setItems(it)
                }
        )
        compositeDisposable.add(
            viewModel.allListViewModel
                .isLoading.subscribe {
                    setLoadMoreProgressBarVisibility(it)
                }
        )
    }

    private fun setLoadMoreProgressBarVisibility(isVisible: Boolean) {
        progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}