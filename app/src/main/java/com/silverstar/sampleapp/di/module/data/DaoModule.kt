package com.silverstar.sampleapp.di.module.data

import com.silverstar.sampleapp.data.dao.ItemDao
import com.silverstar.sampleapp.data.db.ItemDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DaoModule {

    @Provides
    @Singleton
    @JvmStatic
    fun bindItemDao(db: ItemDatabase): ItemDao {
        return db.itemDao()
    }
}