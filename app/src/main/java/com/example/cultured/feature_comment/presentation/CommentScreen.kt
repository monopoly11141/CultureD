package com.example.cultured.feature_comment.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.core.presentation.component.EventImage
import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.core.presentation.preview.EventUiModelProvider
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun CommentScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CommentViewModel = hiltViewModel(),
    eventUiModel: EventUiModel
) {
    LaunchedEffect(true) {
        viewModel.onAction(CommentAction.InitEventUiModel(eventUiModel))
    }

    CommentScreen(
        modifier = modifier,
        navController = navController,
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun CommentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: CommentState,
    onAction: (CommentAction) -> Unit
) {

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
        ) {
            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = state.eventUiModel.title,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )

            EventImage(
                modifier = modifier
                    .fillMaxHeight(0.5f),
                imageUrl = state.eventUiModel.imageUrl,
                contentDescription = "${state.eventUiModel.title} 이미지"
            )

            val uriHandler = LocalUriHandler.current

            Button(
                onClick = {
                    uriHandler.openUri(state.eventUiModel.eventUrl)
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "웹사이트 가기"
                )
            }
        }
    }

}

@PreviewLightDark
@Composable
private fun CommentScreenPreview(@PreviewParameter(EventUiModelProvider::class) eventUiModel: EventUiModel) {

    CultureDTheme {
        CommentScreen(
            navController = rememberNavController(),
            state = CommentState(
                eventUiModel = eventUiModel
            ),
            onAction = {}
        )
    }
}