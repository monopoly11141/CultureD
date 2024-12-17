package com.example.cultured.feature_comment.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.core.presentation.model.toDocumentId
import com.example.cultured.feature_comment.presentation.model.CommentUiModel
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
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(CommentState())
    val state = _state
        .onStart {
            initFirebaseUser()
            initCommentList()
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

            CommentAction.InitCommentList -> {
                initCommentList()
            }

            is CommentAction.OnCommentTitleChange -> {
                onCommentTitleChange(action.commentTitle)
            }

            is CommentAction.OnCommentContentChange -> {
                onCommentContentChange(action.commentContent)
            }

            CommentAction.OnPostComment -> {
                onPostComment()
            }

            is CommentAction.OnDeleteComment -> {
                onDeleteComment(action.commentUiModel)
            }

            is CommentAction.OnEditComment -> {
                onEditComment()
            }

            CommentAction.OnWriteCommentButtonClick -> {
                onWriteCommentButtonClick()
            }

            is CommentAction.OnEditCommentButtonClick -> {
                onEditCommentButtonClick(action.commentUiModel)
            }
        }
    }

    private fun initFirebaseUser() {
        _state.update {
            it.copy(
                currentUser = firebaseAuth.currentUser?.uid ?: throw Exception("User not logged in")
            )
        }
    }

    private fun initCommentList() {

        _state.update {
            it.copy(
                commentList = emptyList()
            )
        }

        firestore.collection(_state.value.eventUiModel.toDocumentId())
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val commentUiModel = document.toObject(CommentUiModel::class.java)

                    _state.update {
                        it.copy(
                            commentList = _state.value.commentList.plus(commentUiModel)
                        )
                    }
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

    private fun onCommentTitleChange(commentTitle: String) {
        _state.update {
            it.copy(
                currentCommentTitle = commentTitle
            )
        }
    }

    private fun onCommentContentChange(commentContent: String) {
        _state.update {
            it.copy(
                currentCommentContent = commentContent
            )
        }
    }

    private fun onPostComment() {

        val commentUiModel = CommentUiModel(
            title = _state.value.currentCommentTitle,
            author = _state.value.currentUser,
            content = _state.value.currentCommentContent
        )

        firestore.collection(_state.value.eventUiModel.toDocumentId())
            .add(commentUiModel)
            .addOnSuccessListener {
                _state.update {
                    it.copy(
                        commentList = _state.value.commentList.plus(commentUiModel),
                        currentCommentTitle = "",
                        currentCommentContent = ""
                    )
                }
            }
            .addOnFailureListener { e ->
                Log.w("CommentViewModel", "${e.message}")
            }

    }

    private fun onEditComment() {
        val commentUiModel = CommentUiModel(
            title = _state.value.currentCommentTitle,
            author = _state.value.currentUser,
            content = _state.value.currentCommentContent
        )

        firestore.collection(_state.value.eventUiModel.toDocumentId())
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val documentCommentUiModel = document.toObject(CommentUiModel::class.java)

                    if (documentCommentUiModel == _state.value.currentEditingComment) {

                        var commentList = _state.value.commentList
                        commentList = commentList.map { comment ->
                            if(comment == _state.value.currentEditingComment) {
                                commentUiModel
                            }else {
                                comment
                            }
                        }

                        firestore.collection(_state.value.eventUiModel.toDocumentId())
                            .document(document.id)
                            .set(commentUiModel)


                        _state.update {
                            it.copy(
                                commentList = commentList
                            )
                        }

                        break
                    }
                }
            }

    }

    private fun onDeleteComment(commentUiModel: CommentUiModel) {
        firestore.collection(_state.value.eventUiModel.toDocumentId())
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val documentCommentUiModel = document.toObject(CommentUiModel::class.java)

                    if (documentCommentUiModel == commentUiModel) {
                        firestore.collection(_state.value.eventUiModel.toDocumentId())
                            .document(document.id)
                            .delete()

                        _state.update {
                            it.copy(
                                commentList = _state.value.commentList.minus(commentUiModel),
                            )
                        }

                        break
                    }
                }
            }
    }


    private fun onWriteCommentButtonClick() {
        _state.update {
            it.copy(
                isCreate = true
            )
        }
    }

    private fun onEditCommentButtonClick(commentUiModel: CommentUiModel) {
        _state.update {
            it.copy(
                currentEditingComment = commentUiModel,
                isCreate = false,
                currentCommentTitle = commentUiModel.title,
                currentCommentContent = commentUiModel.content
            )
        }
    }

}
