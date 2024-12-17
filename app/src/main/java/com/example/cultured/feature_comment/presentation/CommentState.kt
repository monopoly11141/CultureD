package com.example.cultured.feature_comment.presentation

import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.feature_comment.presentation.model.CommentUiModel

data class CommentState(
    val eventUiModel: EventUiModel = EventUiModel(),
    val currentEditingComment : CommentUiModel = CommentUiModel(),
    val isCreate: Boolean = false,
    val currentUser: String = "",
    val currentCommentTitle: String = "",
    val currentCommentContent: String = "",
    val commentList: List<CommentUiModel> = emptyList()
)
