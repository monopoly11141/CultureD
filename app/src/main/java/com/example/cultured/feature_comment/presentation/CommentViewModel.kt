package com.example.cultured.feature_comment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(CommentState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CommentState()
        )

    fun onAction(action: CommentAction) {
        when(action) {

            else -> {}
        }
    }
}