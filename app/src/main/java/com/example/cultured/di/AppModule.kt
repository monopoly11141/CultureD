package com.example.cultured.di

import com.example.cultured.feature_event.data.repository.EventRepositoryImpl
import com.example.cultured.feature_event.domain.repository.EventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesEventRepository(
        eventRetrofit: Retrofit,
    ): EventRepository {
        return EventRepositoryImpl(eventRetrofit)
    }
}