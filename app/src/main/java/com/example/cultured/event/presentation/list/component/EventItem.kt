package com.example.cultured.event.presentation.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.cultured.R
import com.example.cultured.event.presentation.model.EventUiModel
import com.example.cultured.event.presentation.preview.EventUiModelProvider
import com.example.cultured.ui.theme.AppTypography
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun EventItem(
    modifier: Modifier = Modifier,
    eventUiModel: EventUiModel,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        AsyncImage(
            model = eventUiModel.imageUrl,
            contentDescription = "${eventUiModel.title} 대표 이미지",
            placeholder = painterResource(id = R.drawable.loading_image),
            error = painterResource(id = R.drawable.image_not_found)
        )

        Column() {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(eventUiModel.typeList) { typeString ->
                        EventTypeItem(typeString = typeString)
                    }
                }

                EventLocationItem(
                    modifier = Modifier,
                    location = eventUiModel.location
                )

            }

            Text(
                text = eventUiModel.title,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = AppTypography.headlineLarge.fontFamily,
                fontSize = 20.sp
            )

            EventCostItem(
                feeInformation = eventUiModel.feeInformation,
                isFree = eventUiModel.isFree
            )

            Text(
                text = "${eventUiModel.startDate} ~ ${eventUiModel.endDate}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                fontFamily = AppTypography.headlineLarge.fontFamily,
                fontSize = 12.sp
            )

        }
    }
}

@PreviewLightDark
@Composable
private fun EventItemPreview(@PreviewParameter(EventUiModelProvider::class) eventUiModel: EventUiModel) {
    CultureDTheme {
        EventItem(
            eventUiModel = eventUiModel,
            onItemClick = {}
        )
    }
}