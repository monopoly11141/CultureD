package com.example.cultured.feature_login.domain.repository

import com.example.cultured.feature_login.domain.FirebaseAuthError
import com.example.cultured.core.domain.Result
import com.google.firebase.auth.FirebaseAuth

interface LoginRepository {
    fun <T : Any?, E: FirebaseAuthError> signUpWithEmailAndPassword(email: String, password: String) : Result<T, E>
//    fun <T> loginWithEmailAndPassword(email: String, password: String) : Result<T>
}