package com.example.cultured.feature_comment.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cultured.core.presentation.model.EventUiModel

@Composable
fun CommentScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CommentViewModel = hiltViewModel(),
    eventUiModel: EventUiModel
) {
    LaunchedEffect(true) {
        viewModel.onAction(CommentAction.InitEventUiModel(eventUiModel))
    }

    CommentScreen(
        modifier = modifier,
        navController = navController,
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun CommentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: CommentState,
    onAction: (CommentAction) -> Unit
) {
    Log.d("TAG", "${state.eventUiModel}")
}