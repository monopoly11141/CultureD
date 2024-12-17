package com.example.cultured.feature_comment.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun CommentAdd(
    modifier: Modifier = Modifier,
    currentCommentTitle: String,
    onCurrentCommentTitleChange: (String) -> Unit,
    currentCommentContent: String,
    onCurrentCommentContentChange: (String) -> Unit,
    buttonText: String,
    onPostComment: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        TextField(
            modifier = modifier
                .weight(0.2f)
                .fillMaxWidth(),
            value = currentCommentTitle,
            onValueChange = { currentCommentTitle ->
                onCurrentCommentTitleChange.invoke(currentCommentTitle)
            },
        )

        TextField(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth(),
            value = currentCommentContent,
            onValueChange = { currentCommentContent ->
                onCurrentCommentContentChange.invoke(currentCommentContent)
            }
        )

        Button(
            modifier = modifier
                .weight(0.1f)
                .fillMaxWidth(),
            onClick = { onPostComment.invoke() }
        ) {
            Text(buttonText)
        }
    }

}

@PreviewLightDark
@Composable
private fun CommentAddPreview() {
    CultureDTheme {
        CommentAdd(
            currentCommentTitle = "",
            onCurrentCommentTitleChange = {},
            currentCommentContent = "",
            onCurrentCommentContentChange = {},
            buttonText = "댓글 남기기",
            onPostComment = {}
        )
    }
}