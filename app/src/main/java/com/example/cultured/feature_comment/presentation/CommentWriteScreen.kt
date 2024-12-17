package com.example.cultured.feature_comment.presentation

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
import com.example.cultured.feature_comment.presentation.component.CommentAdd
import com.example.cultured.navigation.Screen
import com.example.cultured.ui.theme.CultureDTheme


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
    onAction: (CommentAction) -> Unit
) {
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
            } else {
                onAction.invoke(CommentAction.OnEditComment)
            }

            navController.navigate(state.eventUiModel)
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
            onAction = {}
        )
    }
}