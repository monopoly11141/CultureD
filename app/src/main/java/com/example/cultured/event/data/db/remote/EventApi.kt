package com.example.cultured.event.data.db.remote

import com.example.cultured.event.data.model.EventModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EventApi {
    @GET("1/1000/%20/%20")
    fun boardListPost(@Query("date") date: String): Call<EventModel>
}