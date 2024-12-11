package com.example.cultured.feature_comment.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun CommentScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CommentViewModel = hiltViewModel()
) {
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

}