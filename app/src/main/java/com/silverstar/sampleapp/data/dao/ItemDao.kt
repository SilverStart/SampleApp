package com.silverstar.sampleapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.silverstar.sampleapp.data.entity.Item
import io.reactivex.Observable

@Dao
interface ItemDao {

    @Query("SELECT * from item")
    fun getAll(): Observable<List<Item>>
}