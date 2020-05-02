package com.silverstar.sampleapp.di.module.ui

import com.silverstar.sampleapp.di.module.ui.main.MainModule
import com.silverstar.sampleapp.di.module.vm.ViewModelFactoryModule
import com.silverstar.sampleapp.di.scope.ActivityScoped
import com.silverstar.sampleapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, MainModule::class])
    fun mainActivity(): MainActivity
}