package com.silverstar.sampleapp.data.dao

import androidx.room.*
import com.silverstar.sampleapp.data.entity.Item
import io.reactivex.Observable

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: Item)

    @Delete
    fun delete(vararg items: Item)

    @Query("SELECT * from item")
    fun getAll(): Observable<List<Item>>
}