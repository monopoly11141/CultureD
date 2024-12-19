package com.example.cultured

import android.app.Application
import com.example.cultured.util.KeyUtil
import com.kakao.sdk.common.KakaoSdk
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CultureDApp() : Application() {
    override fun onCreate() {
        KakaoSdk.init(this, KeyUtil.KAKAO_KEY)
        KakaoMapSdk.init(this, KeyUtil.KAKAO_KEY)
        super.onCreate()
    }
}