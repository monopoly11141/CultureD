package com.example.cultured.core.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.cultured.R
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun EventImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String = "이미지",
    contentScale: ContentScale = ContentScale.Fit
) {
    CoilImage(
        imageModel = imageUrl,
        placeHolder = painterResource(id = R.drawable.loading_image),
        error = painterResource(id = R.drawable.image_not_found),
        modifier = modifier,
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}