package com.example.cultured.feature_event.presentation.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cultured.core.presentation.component.EventImage
import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.core.presentation.preview.PreviewModel
import com.example.cultured.core.presentation.preview.PreviewParameterProvider
import com.example.cultured.ui.theme.AppTypography
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun EventItem(
    modifier: Modifier = Modifier,
    eventUiModel: EventUiModel,
    onItemClick: () -> Unit,
    onTypeClick: (typeItem: String) -> Unit,
    onMapIconClick : () -> Unit,
    onFavoriteIconClick: () -> Unit,
    onShareIconClick: () -> Unit
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .clickable { onItemClick() }
    ) {
        EventImage(
            modifier = modifier
                .fillMaxWidth(0.3f)
                .fillMaxHeight(0.3f)
                .padding(4.dp),
            imageUrl = eventUiModel.imageUrl,
            contentDescription = "${eventUiModel.title} 이미지"
        )

        Column {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(eventUiModel.typeList) { typeString ->
                        EventTypeItem(
                            typeString = typeString,
                            onClick = {
                                onTypeClick(typeString)
                            }
                        )
                    }
                }

                EventLocationItem(
                    modifier = modifier,
                    location = eventUiModel.location
                )

            }

            Text(
                modifier = modifier
                    .padding(horizontal = 4.dp),
                text = eventUiModel.title,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = AppTypography.headlineLarge.fontFamily,
                fontSize = 16.sp
            )

            EventCostItem(
                modifier = modifier
                    .padding(horizontal = 4.dp),
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
            )

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconButton(onClick = { onMapIconClick() }) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "지도보기",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.End
                ) {


                    IconButton(onClick = { onFavoriteIconClick() }) {
                        Icon(
                            imageVector = if (eventUiModel.isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (eventUiModel.isFavorite) "즐겨찾기 해제하기" else "즐겨찾기 하기",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(onClick = { onShareIconClick() }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "공유하기",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                }
            }



        }
    }
}

@PreviewLightDark
@Composable
private fun EventItemPreview(@PreviewParameter(PreviewParameterProvider::class) previewModel: PreviewModel) {
    CultureDTheme {
        EventItem(
            eventUiModel = previewModel.eventUiModel,
            onItemClick = {},
            onTypeClick = {},
            onMapIconClick = {},
            onFavoriteIconClick = {},
            onShareIconClick = {}
        )
    }
}