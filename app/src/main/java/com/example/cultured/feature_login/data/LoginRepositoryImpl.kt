package com.example.cultured.feature_login.data

import com.example.cultured.core.domain.Result
import com.example.cultured.core.domain.onError
import com.example.cultured.core.domain.onSuccess
import com.example.cultured.feature_login.domain.FirebaseAuthError
import com.example.cultured.feature_login.domain.repository.LoginRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class LoginRepositoryImpl : LoginRepository {
    private lateinit var auth: FirebaseAuth
    override fun <T : Any?, E : FirebaseAuthError> signUpWithEmailAndPassword(
        email: String,
        password: String
    ): Result<T, E> {
        auth = Firebase.auth

        var user: FirebaseUser? = null

        return Result.Error(FirebaseAuthError.LOGIN_FAILED).onError {}

//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//            user = if (task.isSuccessful) {
//                auth.currentUser!!
//            }else null
//        }
//
//        if (user == null) {
//            return result
//        }else {
//            return Result.success("")
//        }
    }


}