package com.example.cultured.feature_login.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class LoginViewModel @Inject constructor() : ViewModel() {
    private lateinit var auth : FirebaseAuth

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            auth = Firebase.auth

            auth.currentUser?.let { currentUser ->
                _state.update {
                    it.copy(
                        firebaseUser = currentUser
                    )
                }
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

            is LoginAction.OnLoginClick -> TODO()
            is LoginAction.OnSignUpClick -> {
                onSignUpClick()


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
        auth
            .createUserWithEmailAndPassword(_state.value.email, _state.value.password)
            .addOnCompleteListener { task ->
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