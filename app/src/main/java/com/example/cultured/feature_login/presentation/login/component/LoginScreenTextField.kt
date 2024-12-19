package com.example.cultured.feature_login.presentation.login.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.cultured.R
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun LoginScreenTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIconResId: Int? = null,
    trailingIconContentDescription: String = "",
    onTrailingIconClick: () -> Unit = {},
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        visualTransformation = visualTransformation,
        trailingIcon = {
            trailingIconResId?.let {
                Icon(
                    painterResource(id = trailingIconResId),
                    contentDescription = trailingIconContentDescription,
                    modifier = Modifier
                        .scale(0.5f)
                        .clickable { onTrailingIconClick() }
                )
            }


        },
        label = {
            Text(text = labelText)
        }
    )
}

@PreviewLightDark
@Composable
private fun LoginScreenTextFieldPreview() {
    CultureDTheme {
        LoginScreenTextField(
            value = "",
            onValueChange = {},
            labelText = "입력하세요.",
            trailingIconResId = R.drawable.password_show,
        )
    }
}