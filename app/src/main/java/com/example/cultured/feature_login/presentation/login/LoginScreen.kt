package com.example.cultured.feature_login.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.navigation.Screen
import com.example.cultured.ui.theme.CultureDTheme


@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LoginScreen(
        navController = navController,
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    Scaffold(

    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            state.firebaseUser?.let {
                navController.navigate(Screen.EventListScreen.route)
            }

            TextField(
                value = state.email,
                onValueChange = { email ->
                    onAction.invoke(LoginAction.OnEmailChange(email))
                },
                label = {
                    Text("이메일을 입력하세요.")
                }
            )

            TextField(
                value = state.password,
                onValueChange = { password ->
                    onAction.invoke(LoginAction.OnPasswordChange(password))
                },
                label = {
                    Text("비밀번호를 입력하세요.")
                }
            )

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(onClick = {
                    onAction.invoke(LoginAction.OnSignUpClick)
                }) {
                    Text("회원가입")
                }

                Button(
                    onClick = {
                        onAction.invoke(LoginAction.OnLoginClick)
                    }
                ) {
                    Text("로그인")
                }
            }

        }
    }
}

@PreviewLightDark
@Composable
private fun LoginScreenPreview() {
    CultureDTheme {
        LoginScreen(
            navController = rememberNavController(),
            state = LoginState()
        ) {

        }
    }
}