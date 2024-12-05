package com.example.cultured.feature_login.presentation.login

import com.google.firebase.auth.FirebaseUser

data class LoginState(
    val firebaseUser: FirebaseUser? = null,
    val email: String = "",
    val password: String = ""
)