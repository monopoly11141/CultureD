package com.example.cultured.feature_comment.presentation

sealed interface CommentEvent {
    data object OnNavigateUp : CommentEvent
}