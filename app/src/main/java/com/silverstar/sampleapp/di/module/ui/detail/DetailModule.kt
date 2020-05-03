package com.silverstar.sampleapp.di.module.ui.detail

import androidx.lifecycle.ViewModel
import com.silverstar.sampleapp.di.ViewModelKey
import com.silverstar.sampleapp.ui.detail.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    fun detailViewModel(detailViewModel: DetailViewModel): ViewModel
}