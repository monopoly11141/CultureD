package com.example.cultured.core.presentation.preview

import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.feature_comment.presentation.model.CommentUiModel

data class PreviewModel(
    val eventUiModel: EventUiModel,
    val eventUiModelList : List<EventUiModel>,
    val commentUiModel: CommentUiModel,
    val commentUiModelList: List<CommentUiModel>
)
