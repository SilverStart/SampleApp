package com.silverstar.sampleapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.silverstar.sampleapp.data.dao.ItemDao
import com.silverstar.sampleapp.data.entity.Item

@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}