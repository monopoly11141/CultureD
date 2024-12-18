package com.example.cultured.feature_comment.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.core.presentation.component.EventImage
import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.core.presentation.preview.PreviewModel
import com.example.cultured.core.presentation.preview.PreviewParameterProvider
import com.example.cultured.feature_comment.presentation.component.CommentItem
import com.example.cultured.navigation.Screen
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun CommentScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CommentViewModel = hiltViewModel(),
    eventUiModel: EventUiModel? = null
) {
    LaunchedEffect(true) {
        eventUiModel?.let {
            viewModel.onAction(CommentAction.InitEventUiModel(it))
        }
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

            EventImage(
                modifier = modifier
                    .weight(0.4f),
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

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier
                        .fillMaxHeight()
                        .wrapContentSize()
                        .padding(8.dp),
                    text = "댓글 (${state.commentList.size}개)",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )

                Button(
                    onClick = {
                        onAction.invoke(CommentAction.OnWriteCommentButtonClick)
                        navController.navigate(Screen.CommentWriteScreen.route)
                    },
                    modifier = modifier
                        .fillMaxHeight()
                        .wrapContentSize()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "댓글 쓰기"
                    )
                }
            }

            LazyColumn(
                modifier = modifier
                    .weight(1f)
            ) {
                items(state.commentList) { commentUiModel ->
                    Log.d("CommentScreen", commentUiModel.toString())
                    CommentItem(
                        modifier = modifier
                            .padding(2.dp),
                        commentUiModel = commentUiModel,
                        isCommentByCurrentUser = commentUiModel.author == state.currentUser,
                        onEditItemClick = {
                            onAction.invoke(CommentAction.OnEditCommentButtonClick(commentUiModel))
                            navController.navigate(Screen.CommentWriteScreen.route)
                        },
                        onDeleteItemClick = {
                            onAction.invoke(CommentAction.OnDeleteComment(commentUiModel))
                        }
                    )

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 1.dp,
                    )

                }

            }
        }
    }

}

@PreviewLightDark
@Composable
private fun CommentScreenPreview(
    @PreviewParameter(PreviewParameterProvider::class) previewModel: PreviewModel
) {
    CultureDTheme {
        CommentScreen(
            navController = rememberNavController(),
            state = CommentState(
                eventUiModel = previewModel.eventUiModel,
                commentList = previewModel.commentUiModelList
            ),
            onAction = {}
        )
    }
}