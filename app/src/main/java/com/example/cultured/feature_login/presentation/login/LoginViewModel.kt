package com.example.cultured.feature_login.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            _state.update {
                it.copy(
                    firebaseUser = auth.currentUser
                )
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

        auth.createUserWithEmailAndPassword(_state.value.email, _state.value.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _state.update {
                    it.copy(
                        firebaseUser = auth.currentUser
                    )
                }
            } else {

            }
        }
    }

    private fun onLoginClick() {
        auth.signInWithEmailAndPassword(_state.value.email, _state.value.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _state.update {
                    it.copy(
                        firebaseUser = auth.currentUser
                    )
                }
            } else {

            }
        }
    }


}