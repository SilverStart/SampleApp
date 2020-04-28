package com.silverstar.sampleapp.data.pojo

import com.google.gson.annotations.SerializedName

data class ItemFromServer(
    @SerializedName("name") val name: String = "",
    @SerializedName("thumbnail") val thumbnailUrl: String = "",
    @SerializedName("description") val description: Description = Description(),
    @SerializedName("rate") val rate: Float = 0f
)