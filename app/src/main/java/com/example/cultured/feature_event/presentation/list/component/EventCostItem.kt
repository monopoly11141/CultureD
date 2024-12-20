package com.example.cultured.feature_event.presentation.list.component

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.example.cultured.core.presentation.preview.PreviewModel
import com.example.cultured.core.presentation.preview.PreviewParameterProvider
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun EventCostItem(
    modifier: Modifier = Modifier,
    feeInformation: String,
    isFree: Boolean
) {

    Text(
        text = getFeeString(feeInformation, isFree),
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
    )
}


private fun getFeeString(
    feeInformation: String,
    isFree: Boolean
): String {
    if (isFree) {
        return "무료"
    }
    return feeInformation
}

@PreviewLightDark
@Composable
private fun EventCostItemPreview(@PreviewParameter(PreviewParameterProvider::class) previewModel: PreviewModel) {
    CultureDTheme {
        EventCostItem(
            feeInformation = previewModel.eventUiModel.feeInformation,
            isFree = previewModel.eventUiModel.isFree
        )
    }
}