package com.example.cultured.feature_comment.presentation.model

import java.time.LocalDate

data class CommentUiModel(
    val title: String = "",
    val author: String = "",
    val content: String = "",
    val timeStamp: String = ""
)