package com.example.cultured.feature_comment.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
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
    isCommentByCurrentUser: Boolean,
    onEditItemClick: () -> Unit,
    onDeleteItemClick: () -> Unit
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

        if (isCommentByCurrentUser) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "댓글 수정하기",
                    modifier = modifier
                        .padding(horizontal = 4.dp)
                        .clickable {
                            onEditItemClick.invoke()
                        }
                )

                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "댓글 삭제하기",
                    modifier = modifier
                        .padding(horizontal = 4.dp)
                        .clickable {
                            onDeleteItemClick.invoke()
                        }
                )
            }
        }

    }
}

@PreviewLightDark
@Composable
private fun CommentItemPreview(@PreviewParameter(PreviewParameterProvider::class) previewModel: PreviewModel) {
    CultureDTheme {
        CommentItem(
            commentUiModel = previewModel.commentUiModel,
            isCommentByCurrentUser = true,
            onEditItemClick = {},
            onDeleteItemClick = {}
        )
    }
}