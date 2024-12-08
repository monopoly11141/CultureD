package com.example.cultured.feature_event.presentation.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cultured.feature_event.presentation.model.EventUiModel
import com.example.cultured.feature_event.presentation.preview.EventUiModelProvider
import com.example.cultured.ui.theme.AppTypography
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun EventTypeItem(
    modifier: Modifier = Modifier,
    typeString: String,
    fontSize: TextUnit = 12.sp,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(2.dp))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Text(
            text = typeString,
            fontSize = fontSize,
            modifier = modifier
                .padding(horizontal = 4.dp, vertical = 2.dp),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = AppTypography.headlineLarge.fontFamily
        )
    }
}

@PreviewLightDark
@Composable
private fun EventTypeItemPrev(@PreviewParameter(EventUiModelProvider::class) eventUiModel: EventUiModel) {
    CultureDTheme {
        EventTypeItem(
            typeString = eventUiModel.typeList[0],
            onClick = {}
        )
    }
}