package com.example.cultured.feature_login.domain.repository

import com.example.cultured.feature_login.domain.FirebaseAuthError
import com.example.cultured.core.domain.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface LoginRepository {
    fun getCurrentUser(): Result<FirebaseUser, FirebaseAuthError>
    fun signUpWithEmailAndPassword(email: String, password: String): Result<FirebaseUser, FirebaseAuthError>
    fun loginWithEmailAndPassword(email: String, password: String): Result<FirebaseUser, FirebaseAuthError>
}