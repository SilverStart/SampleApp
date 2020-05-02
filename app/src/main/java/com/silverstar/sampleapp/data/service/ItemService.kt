package com.silverstar.sampleapp.data.service

import com.google.gson.annotations.SerializedName
import com.silverstar.sampleapp.data.pojo.ItemFromServer
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemService {

    @GET("App/json/{page}.json")
    fun getItemBy(@Path("page") page: Int): Observable<ItemResult>

    data class ItemResult(
        @SerializedName("data")
        val data: DataResult
    ) {

        data class DataResult(
            @SerializedName("totalCount")
            val totalCount: Int,
            @SerializedName("product")
            val product: List<ItemFromServer>
        )
    }

}