package com.example.cultured.feature_login.presentation.util

import android.content.Context
import com.example.cultured.R
import com.example.cultured.feature_login.domain.FirebaseAuthError

fun FirebaseAuthError.toString(context: Context): String {
    val resId = when (this) {
        FirebaseAuthError.SIGNUP_FAILED -> R.string.error_signup_failed

        FirebaseAuthError.LOGIN_FAILED -> R.string.error_login_failed
    }

    return context.getString(resId)
}