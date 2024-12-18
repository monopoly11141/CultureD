package com.example.cultured

import android.app.Application
import android.util.Log
import com.example.cultured.util.KeyUtil
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltAndroidApp
class CultureDApp(): Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, KeyUtil.KAKAO_KEY)

        var keyHash = Utility.getKeyHash(this)
        Log.e("CultureDApp", "해시 키 값 : ${keyHash}")
    }
}