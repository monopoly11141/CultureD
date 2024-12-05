package com.example.cultured.feature_login.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            LoginState()
        )

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = action.email
                    )
                }
            }
            is LoginAction.OnPasswordChange ->  {
                _state.update {
                    it.copy(
                        password = action.password
                    )
                }
            }
            is LoginAction.OnLoginClick -> TODO()
            is LoginAction.OnSignUpClick -> TODO()

        }
    }
}