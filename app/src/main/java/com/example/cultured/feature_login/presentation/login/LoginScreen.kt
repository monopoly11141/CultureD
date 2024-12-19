package com.example.cultured.feature_login.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.core.presentation.util.ObserveAsEvents
import com.example.cultured.feature_login.presentation.login.component.LoginScreenButton
import com.example.cultured.feature_login.presentation.login.component.LoginScreenTextField
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
            modifier = modifier,
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
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "서울 문화생활의 시작",
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Light
                )

                Text(
                    text = "CultureD",
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LoginScreenTextField(
                    modifier = modifier
                        .fillMaxWidth(0.8f),
                    value = state.email,
                    onValueChange = { email ->
                        onAction.invoke(LoginAction.OnEmailChange(email))
                    },
                    labelText = "이메일을 입력하세요"
                )

                LoginScreenTextField(
                    modifier = modifier
                        .fillMaxWidth(0.8f),
                    value = state.password,
                    onValueChange = { password ->
                        onAction.invoke(LoginAction.OnPasswordChange(password))
                    },
                    labelText = "비밀번호를 입력하세요",
                    visualTransformation = state.passwordInputFieldInfo.visualTransformation,
                    trailingIconResId = state.passwordInputFieldInfo.resId,
                    trailingIconContentDescription = state.passwordInputFieldInfo.contentDescription,
                    onTrailingIconClick = {
                        onAction.invoke(LoginAction.OnPasswordIconClick)
                    }
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                LoginScreenButton(
                    modifier = modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    buttonText = "회원가입",
                    onButtonClick = {
                        onAction.invoke(LoginAction.OnSignUpClick)
                    }
                )

                LoginScreenButton(
                    modifier = modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    buttonText = "로그인",
                    onButtonClick = {
                        onAction.invoke(LoginAction.OnLoginClick)
                    }
                )

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
            eventFlow = emptyFlow(),
            onAction = {}
        )
    }
}