package com.example.cultured.feature_event.domain.repository

import com.example.cultured.feature_event.data.db.remote.EventApi

interface EventRepository {
    fun getEventApi() : EventApi
}