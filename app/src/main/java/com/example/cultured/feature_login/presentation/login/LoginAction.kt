package com.example.cultured.feature_login.presentation.login

import android.content.Context

sealed interface LoginAction {
    data class OnEmailChange(val email: String) : LoginAction
    data class OnPasswordChange(val password: String) : LoginAction

    data object OnLoginClick : LoginAction
    data object OnSignUpClick : LoginAction
    data class OnKakaoLoginClick(val context: Context) : LoginAction

    data object OnPasswordIconClick: LoginAction
}