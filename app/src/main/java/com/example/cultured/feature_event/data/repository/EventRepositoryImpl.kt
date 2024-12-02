package com.example.cultured.feature_event.data.repository

import com.example.cultured.feature_event.data.db.remote.EventApi
import com.example.cultured.feature_event.domain.repository.EventRepository
import retrofit2.Retrofit
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventRetrofit: Retrofit
) : EventRepository {

    override fun getEventApi(): EventApi {
        return eventRetrofit.create(EventApi::class.java)
    }

}