package com.example.cultured.feature_login.presentation.login

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.core.presentation.util.ObserveAsEvents
import com.example.cultured.feature_login.presentation.util.toString
import com.example.cultured.navigation.Screen
import com.example.cultured.ui.theme.CultureDTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    if (viewModel.state.collectAsStateWithLifecycle().value.firebaseUser == null) {
        LoginScreen(
            navController = navController,
            state = viewModel.state.collectAsStateWithLifecycle().value,
            eventFlow = viewModel.eventChannel,
            onAction = { action ->
                viewModel.onAction(action)
            }
        )
    } else {
        navController.navigate(Screen.EventListScreen.route)
    }

}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: LoginState,
    eventFlow: Flow<LoginEvent>,
    onAction: (LoginAction) -> Unit
) {

    val context = LocalContext.current

    ObserveAsEvents(eventFlow = eventFlow) { event ->
        when (event) {
            is LoginEvent.Error -> {
                Toast.makeText(context, event.error.toString(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(key1 = state.firebaseUser) {
        if (state.firebaseUser != null) {
            navController.navigate(Screen.EventListScreen.route)
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

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
            state = LoginState(),
            eventFlow = emptyFlow()
        ) {

        }
    }
}