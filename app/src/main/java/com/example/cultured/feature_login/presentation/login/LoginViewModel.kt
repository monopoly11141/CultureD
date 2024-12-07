package com.example.cultured.feature_login.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cultured.core.domain.onError
import com.example.cultured.core.domain.onSuccess
import com.example.cultured.feature_login.domain.repository.LoginRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    private lateinit var auth: FirebaseAuth

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            auth = Firebase.auth

            repository.getCurrentUser()
                .onSuccess { currentUser ->
                    _state.update {
                        it.copy(
                            firebaseUser = currentUser
                        )
                    }
                }.onError { error ->
                    Log.d("LoginViewModel", "user not found: $error")
                    //
                }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            LoginState()
        )

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnEmailChange -> {
                onEmailChange(action.email)
            }
            is LoginAction.OnPasswordChange -> {
                onPasswordChange(action.password)
            }
            is LoginAction.OnSignUpClick -> {
                onSignUpClick()
            }
            is LoginAction.OnLoginClick -> {
                onLoginClick()
            }

        }
    }

    private fun onEmailChange(email: String) {
        _state.update {
            it.copy(
                email = email
            )
        }
    }

    private fun onPasswordChange(password: String) {
        _state.update {
            it.copy(
                password = password
            )
        }
    }

    private fun onSignUpClick() {
        repository.signUpWithEmailAndPassword(_state.value.email, _state.value.password)
            .onSuccess { currentUser ->
                _state.update {
                    it.copy(
                        firebaseUser = currentUser
                    )
                }
            }.onError { error ->
                Log.d("LoginViewModel", "signup not worked : $error")
                //
            }
    }

    private fun onLoginClick() {
        repository.loginWithEmailAndPassword(_state.value.email, _state.value.password)
            .onSuccess { currentUser ->
                _state.update {
                    it.copy(
                        firebaseUser = currentUser
                    )
                }
            }.onError { error ->
                Log.d("LoginViewModel", "login not worked : $error")
                //
            }
    }


}