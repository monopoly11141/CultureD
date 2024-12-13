package com.example.cultured.feature_comment.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cultured.core.presentation.preview.PreviewModel
import com.example.cultured.core.presentation.preview.PreviewParameterProvider
import com.example.cultured.feature_comment.presentation.model.CommentUiModel
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    commentUiModel: CommentUiModel,
    isCommentByCurrentUser: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            text = commentUiModel.author,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            text = commentUiModel.title,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            text = commentUiModel.content,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp
        )

    }
}

@PreviewLightDark
@Composable
private fun CommentItemPreview(@PreviewParameter(PreviewParameterProvider::class) previewModel: PreviewModel) {
    CultureDTheme {
        CommentItem(
            commentUiModel = previewModel.commentUiModel,
            isCommentByCurrentUser = true
        )
    }
}