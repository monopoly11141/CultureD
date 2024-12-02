package com.example.cultured.feature_event.data.db.remote

import com.example.cultured.feature_event.data.model.EventModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApi {
    @GET("1/1000/%20/%20/{DATE}")
    fun getEventModelWithDate(@Path("DATE") date: String): Call<EventModel>
}