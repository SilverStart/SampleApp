package com.silverstar.sampleapp.ui.liked

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.silverstar.sampleapp.R
import com.silverstar.sampleapp.ui.adapter.ItemAdapter
import com.silverstar.sampleapp.ui.detail.DetailActivity
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LikedListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LikedListHoldViewModel

    private val compositeDisposable = CompositeDisposable()

    private lateinit var recyclerView: RecyclerView

    private val adapter = ItemAdapter(
        {
            startActivity(
                Intent(requireContext(), DetailActivity::class.java)
                    .putExtra(DetailActivity.EXTRA_ITEM, Gson().toJson(it))
            )
        },
        {
            viewModel.updateLikedState(it)
        })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_liked_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(LikedListHoldViewModel::class.java)

        recyclerView = view.findViewById(R.id.recycler_view)

        initRecyclerView()
        subscribe()
        viewModel.init()
    }

    override fun onDestroyView() {
        compositeDisposable.dispose()
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        recyclerView.adapter = adapter
    }

    private fun subscribe() {
        compositeDisposable.add(
            viewModel.likedListViewModel
                .list.subscribe {
                    adapter.setItems(it)
                }
        )
    }
}