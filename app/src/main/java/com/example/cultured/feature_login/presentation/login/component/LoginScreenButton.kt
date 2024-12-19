package com.example.cultured.feature_login.presentation.login.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun LoginScreenButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
            onButtonClick()
        },
    ) {
        Text(
            text = buttonText
        )
    }
}

@PreviewLightDark
@Composable
private fun LoginScreenButtonPreview() {
    CultureDTheme {
        LoginScreenButton(
            buttonText = "",
            onButtonClick = {}
        )
    }
}