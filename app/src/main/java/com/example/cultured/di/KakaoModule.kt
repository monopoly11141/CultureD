package com.example.cultured.di

import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoModule {

    @Provides
    @Singleton
    fun providesKakaoUserAPiClient() : UserApiClient {
        return UserApiClient.instance
    }

}