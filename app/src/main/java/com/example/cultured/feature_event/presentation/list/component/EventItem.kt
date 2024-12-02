package com.example.cultured.feature_event.presentation.list.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cultured.R
import com.example.cultured.feature_event.presentation.model.EventUiModel
import com.example.cultured.feature_event.presentation.preview.EventUiModelProvider
import com.example.cultured.ui.theme.AppTypography
import com.example.cultured.ui.theme.CultureDTheme
import com.skydoves.landscapist.coil.CoilImage

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
        CoilImage(
            imageModel = eventUiModel.imageUrl,
            placeHolder = painterResource(id = R.drawable.loading_image),
            error = painterResource(id = R.drawable.image_not_found),
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .fillMaxHeight(0.3f)
                .padding(4.dp),
            contentDescription = "${eventUiModel.title} 대표 이미지",
            contentScale = ContentScale.Fit
        )

        Column {

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
                modifier = Modifier
                    .padding(4.dp),
                text = eventUiModel.title,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = AppTypography.headlineLarge.fontFamily,
                fontSize = 16.sp
            )

            EventCostItem(
                feeInformation = eventUiModel.feeInformation,
                isFree = eventUiModel.isFree
            )

            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                text = "${eventUiModel.startDate} ~ ${eventUiModel.endDate}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                fontFamily = AppTypography.headlineLarge.fontFamily,
                fontSize = 12.sp,
                textAlign = TextAlign.End
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