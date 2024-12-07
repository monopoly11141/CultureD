package com.example.cultured.feature_login.domain

import com.example.cultured.core.domain.Error

enum class FirebaseAuthError: Error {
    SIGNUP_FAILED,
    LOGIN_FAILED
}