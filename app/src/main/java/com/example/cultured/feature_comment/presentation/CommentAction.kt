package com.example.cultured.feature_comment.presentation

import com.example.cultured.core.presentation.model.EventUiModel
import org.w3c.dom.Comment

sealed class CommentAction {
    data class InitEventUiModel(val eventUiModel: EventUiModel) : CommentAction()
    data class OnCommentTitleChange(val commentTitle: String) : CommentAction()
    data class OnCommentContentChange(val commentContent: String) : CommentAction()
    data object OnPostComment : CommentAction()
}