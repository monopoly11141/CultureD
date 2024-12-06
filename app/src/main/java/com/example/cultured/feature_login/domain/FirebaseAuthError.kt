package com.example.cultured.feature_login.domain

import com.example.cultured.core.domain.Error

enum class FirebaseAuthError : Error {
    LOGIN_FAILED,
    USER_NOT_LOGGED_IN
}