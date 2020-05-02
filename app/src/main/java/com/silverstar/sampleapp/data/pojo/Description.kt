package com.silverstar.sampleapp.data.pojo

import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("imagePath") val imageUrl: String = "",
    @SerializedName("subject") val subject: String = "",
    @SerializedName("price") val price: Int = 0
)