package com.silverstar.sampleapp.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.silverstar.sampleapp.R
import com.silverstar.sampleapp.ui.liked.LikedListFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, LikedListFragment())
            .commit()
    }
}
