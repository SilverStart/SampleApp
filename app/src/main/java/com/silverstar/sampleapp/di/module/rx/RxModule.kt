package com.silverstar.sampleapp.di.module.rx

import com.silverstar.sampleapp.rx.SchedulerProvider
import com.silverstar.sampleapp.rx.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RxModule {

    @Binds
    @Singleton
    fun bindSchedulerProvider(schedulerProviderImpl: SchedulerProviderImpl): SchedulerProvider
}