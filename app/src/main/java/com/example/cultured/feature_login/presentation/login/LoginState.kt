package com.example.cultured.feature_login.presentation.login

import androidx.compose.ui.text.input.VisualTransformation
import com.example.cultured.R
import com.google.firebase.auth.FirebaseUser

data class LoginState(
    val firebaseUser: FirebaseUser? = null,
    val email: String = "",
    val password: String = "",
    val passwordInputFieldInfo: PasswordInputFieldInfo = PasswordInputFieldInfo()
)

data class PasswordInputFieldInfo(
    val resId: Int = R.drawable.password_show,
    val contentDescription: String = "",
    val visualTransformation: VisualTransformation = VisualTransformation.None
)