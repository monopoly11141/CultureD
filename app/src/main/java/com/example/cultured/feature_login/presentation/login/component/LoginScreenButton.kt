package com.example.cultured.feature_login.presentation.login.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun LoginScreenButton(
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    borderStroke: BorderStroke? = null,
    buttonText: String,
    textFontWeight: FontWeight = FontWeight.Normal,
    onButtonClick: () -> Unit
) {
    Button(
        modifier = modifier,
        border = borderStroke,
        onClick = {
            onButtonClick()
        },
        colors = buttonColors
    ) {
        Text(
            text = buttonText,
            fontWeight = textFontWeight
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