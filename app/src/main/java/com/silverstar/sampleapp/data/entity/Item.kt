package com.silverstar.sampleapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "item")
data class Item(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "rate")
    val rate: Float,
    @ColumnInfo(name = "liked")
    val liked: Boolean
)