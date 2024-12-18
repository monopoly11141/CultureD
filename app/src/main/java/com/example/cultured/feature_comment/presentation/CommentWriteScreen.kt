package com.example.cultured.feature_comment.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.core.presentation.preview.PreviewModel
import com.example.cultured.core.presentation.preview.PreviewParameterProvider
import com.example.cultured.core.presentation.util.ObserveAsEvents
import com.example.cultured.feature_comment.presentation.component.CommentAdd
import com.example.cultured.navigation.Screen
import com.example.cultured.ui.theme.CultureDTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@Composable
fun CommentWriteScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CommentViewModel = hiltViewModel()
) {

    CommentWriteScreen(
        modifier = modifier,
        navController = navController,
        state = viewModel.state.collectAsStateWithLifecycle().value,
        eventFlow = viewModel.eventChannel,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}


@Composable
fun CommentWriteScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: CommentState,
    eventFlow: Flow<CommentEvent>,
    onAction: (CommentAction) -> Unit
) {

    ObserveAsEvents(eventFlow = eventFlow) { event ->
        when (event) {
            CommentEvent.OnNavigateUp -> {
                Log.d("CommentScreen", "navigate up working?")
                navController.navigateUp()
            }
        }
    }

    CommentAdd(
        currentCommentTitle = state.currentCommentTitle,
        onCurrentCommentTitleChange = { title ->
            onAction.invoke(CommentAction.OnCommentTitleChange(title))
        },
        currentCommentContent = state.currentCommentContent,
        onCurrentCommentContentChange = { content ->
            onAction.invoke(CommentAction.OnCommentContentChange(content))
        },
        buttonText = if(state.isCreate) "댓글 남기기" else "댓글 수정하기",
        onPostComment = {
            if(state.isCreate) {
                onAction.invoke(CommentAction.OnPostComment)
                //navController.navigate(state.eventUiModel)
            } else {
                onAction.invoke(CommentAction.OnEditComment)
                //navController.navigate(state.eventUiModel)
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun ComposeWriteScreenPreview(
    @PreviewParameter(PreviewParameterProvider::class) previewModel: PreviewModel
) {
    CultureDTheme {
        CommentWriteScreen(
            navController = rememberNavController(),
            state = CommentState(
                eventUiModel = previewModel.eventUiModel,
                commentList = previewModel.commentUiModelList
            ),
            eventFlow = emptyFlow(),
            onAction = {}
        )
    }
}