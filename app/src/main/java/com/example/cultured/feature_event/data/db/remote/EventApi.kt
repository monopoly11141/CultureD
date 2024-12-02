package com.example.cultured.feature_event.data.db.remote

import com.example.cultured.feature_event.data.model.EventModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {
    @GET("1/1000/%20/%20/{START_DATE}")
    fun getEventModel(@Path("START_DATE") date: String): Call<EventModel>
}