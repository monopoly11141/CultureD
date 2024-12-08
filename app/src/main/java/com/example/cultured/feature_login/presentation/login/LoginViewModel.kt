package com.example.cultured.feature_login.presentation.login

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cultured.R
import com.example.cultured.feature_login.domain.FirebaseAuthError
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                    firebaseUser = auth.currentUser,
                    passwordInputFieldInfo = PasswordInputFieldInfo(
                        resId = R.drawable.password_hide,
                        contentDescription = "비밀번호 숨기기",
                        visualTransformation = PasswordVisualTransformation()
                    )
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            LoginState()
        )

    private val _eventChannel = Channel<LoginEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

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

            is LoginAction.OnPasswordIconClick -> {
                onPasswordIconClick()
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
                viewModelScope.launch {
                    _eventChannel.send(LoginEvent.Error(error = FirebaseAuthError.SIGNUP_FAILED))
                }
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
                viewModelScope.launch {
                    _eventChannel.send(LoginEvent.Error(error = FirebaseAuthError.LOGIN_FAILED))
                }
            }
        }
    }

    private fun onPasswordIconClick() {

        val passwordIconInfo = _state.value.passwordInputFieldInfo
        _state.update {
            it.copy(
                passwordInputFieldInfo = if (passwordIconInfo.resId == R.drawable.password_hide) {
                    PasswordInputFieldInfo(
                        resId = R.drawable.password_show,
                        contentDescription = "비밀번호 숨기기",
                        visualTransformation = VisualTransformation.None
                    )
                } else {
                    PasswordInputFieldInfo(
                        resId = R.drawable.password_hide,
                        contentDescription = "비밀번호 보여주기",
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
            )
        }
    }


}