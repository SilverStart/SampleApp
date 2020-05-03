package com.silverstar.sampleapp.di.module.data

import android.content.Context
import androidx.room.Room
import com.silverstar.sampleapp.data.db.ItemDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DbModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideDb(applicationContext: Context): ItemDatabase {
        return Room.databaseBuilder(applicationContext, ItemDatabase::class.java, "item-db")
            .build()
    }
}