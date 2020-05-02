package com.silverstar.sampleapp.di.module.vm

import androidx.lifecycle.ViewModelProvider
import com.silverstar.sampleapp.di.ViewModelFactory
import com.silverstar.sampleapp.di.scope.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {

    @ActivityScoped
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}