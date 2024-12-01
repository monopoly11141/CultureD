package com.example.cultured.event.presentation.list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.ui.theme.CultureDTheme

@Composable
fun EventListScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: EventListViewModel = hiltViewModel()
) {
    EventListScreen(
        modifier = modifier,
        navController = navController,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun EventListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onAction: (EventListAction) -> Unit
) {
    Text("Hello EventListScreen")
}

@Preview
@Composable
private fun EventListScreenPreview() {
    CultureDTheme {
        EventListScreen(
            navController = rememberNavController(),
            onAction = {}
        )
    }
}