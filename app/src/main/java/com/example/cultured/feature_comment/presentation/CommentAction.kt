package com.example.cultured.feature_comment.presentation

import com.example.cultured.core.presentation.model.EventUiModel

sealed class CommentAction {
    data class InitEventUiModel(val eventUiModel: EventUiModel) : CommentAction()
}