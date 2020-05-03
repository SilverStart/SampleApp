package com.silverstar.sampleapp.di.module.data

import com.silverstar.sampleapp.data.service.ItemService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object ServiceModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideItemService(retrofit: Retrofit): ItemService {
        return retrofit.create(ItemService::class.java)
    }
}