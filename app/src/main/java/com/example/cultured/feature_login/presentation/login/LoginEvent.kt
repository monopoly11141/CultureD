package com.example.cultured.feature_login.presentation.login

import com.example.cultured.feature_login.domain.FirebaseAuthError

sealed interface LoginEvent {
    data class Error(val error: FirebaseAuthError) : LoginEvent
}