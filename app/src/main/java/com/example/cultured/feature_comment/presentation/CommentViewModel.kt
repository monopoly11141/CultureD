package com.example.cultured.feature_comment.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cultured.core.presentation.model.EventUiModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {
    private val _state = MutableStateFlow(CommentState())
    val state = _state
        .onStart {
            _state.update {
                it.copy(
                    currentUser = firebaseAuth.currentUser?.uid ?: throw Exception("User not logged in")
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CommentState()
        )

    fun onAction(action: CommentAction) {
        when (action) {
            is CommentAction.InitEventUiModel -> {
                initEventUiModel(action.eventUiModel)
            }
        }
    }

    private fun initEventUiModel(eventUiModel: EventUiModel) {
        _state.update {
            it.copy(
                eventUiModel = eventUiModel
            )
        }
    }
}
