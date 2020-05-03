package com.silverstar.sampleapp.di.module.ui

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.DaggerApplication

@Module
interface ContextModule {

    @Binds
    fun bindContext(daggerApplication: DaggerApplication): Context
}