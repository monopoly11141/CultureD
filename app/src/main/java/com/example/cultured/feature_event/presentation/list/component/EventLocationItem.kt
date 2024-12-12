package com.example.cultured.feature_event.presentation.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.core.presentation.preview.EventUiModelProvider
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun EventLocationItem(
    modifier: Modifier = Modifier,
    location: String
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Place,
            contentDescription = "장소 아이콘",
            tint = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = location,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
    }
}

@PreviewLightDark
@Composable
private fun EventLocationItemPreview(@PreviewParameter(EventUiModelProvider::class) eventUiModel: EventUiModel) {
    CultureDTheme {
        EventLocationItem(
            location = eventUiModel.location
        )
    }
}