package com.silverstar.sampleapp.di.module.ui.main

import androidx.lifecycle.ViewModel
import com.silverstar.sampleapp.di.ViewModelKey
import com.silverstar.sampleapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(mainViewModel: MainViewModel): ViewModel
}