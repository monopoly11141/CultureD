package com.example.cultured.feature_login.data

import com.example.cultured.core.domain.Result
import com.example.cultured.feature_login.domain.FirebaseAuthError
import com.example.cultured.feature_login.domain.repository.LoginRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class LoginRepositoryImpl() : LoginRepository {

    private lateinit var auth: FirebaseAuth

    override fun getCurrentUser(): Result<FirebaseUser, FirebaseAuthError> {
        auth = Firebase.auth

        return if(auth.currentUser == null) {
            Result.Error(FirebaseAuthError.USER_NOT_LOGGED_IN)
        }else {
            Result.Success(auth.currentUser!!)
        }

    }

    override fun signUpWithEmailAndPassword(email: String, password: String): Result<FirebaseUser, FirebaseAuthError> {
        auth = Firebase.auth

        var user: FirebaseUser? = null

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { _ ->
            user = auth.currentUser
        }

        return if (user == null) {
            Result.Error(FirebaseAuthError.LOGIN_FAILED)
        }else {
            Result.Success(user!!)
        }
    }

    override fun loginWithEmailAndPassword(email: String, password: String): Result<FirebaseUser, FirebaseAuthError> {
        auth = Firebase.auth

        var user: FirebaseUser? = null

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { _ ->
            user = auth.currentUser
        }

        return if (user == null) {
            Result.Error(FirebaseAuthError.LOGIN_FAILED)
        }else {
            Result.Success(user!!)
        }
    }


}