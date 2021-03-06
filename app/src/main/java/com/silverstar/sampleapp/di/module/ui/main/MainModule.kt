package com.silverstar.sampleapp.di.module.ui.main

import androidx.lifecycle.ViewModel
import com.silverstar.sampleapp.business.LoadItemByPageProcessorHolder
import com.silverstar.sampleapp.business.base.ProcessorHolder
import com.silverstar.sampleapp.data.pojo.ItemFromServer
import com.silverstar.sampleapp.di.ViewModelKey
import com.silverstar.sampleapp.ui.all.AllListFragment
import com.silverstar.sampleapp.ui.all.AllListHoldViewModel
import com.silverstar.sampleapp.ui.liked.LikedListFragment
import com.silverstar.sampleapp.ui.liked.LikedListHoldViewModel
import com.silverstar.sampleapp.ui.main.MainViewModel
import com.silverstar.sampleapp.utils.Result
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    fun bindLoadItemByPageProcessorHolder(processorHolder: LoadItemByPageProcessorHolder): ProcessorHolder<Int, Result<List<ItemFromServer>>>

    @Binds
    @IntoMap
    @ViewModelKey(AllListHoldViewModel::class)
    fun allListHoldViewModel(allListHoldViewModel: AllListHoldViewModel): ViewModel

    @ContributesAndroidInjector
    fun allListFragment(): AllListFragment

    @Binds
    @IntoMap
    @ViewModelKey(LikedListHoldViewModel::class)
    fun likedListHoldViewModel(likedListHoldViewModel: LikedListHoldViewModel): ViewModel

    @ContributesAndroidInjector
    fun likedListFragment(): LikedListFragment
}