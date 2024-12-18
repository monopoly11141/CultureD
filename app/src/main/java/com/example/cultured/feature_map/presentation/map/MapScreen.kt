package com.example.cultured.feature_map.presentation.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.feature_event.presentation.list.EventListAction
import com.example.cultured.feature_event.presentation.list.EventListEvent
import com.example.cultured.feature_event.presentation.list.EventListScreen
import com.example.cultured.feature_event.presentation.list.EventListState
import com.example.cultured.feature_event.presentation.list.EventListViewModel
import com.example.cultured.ui.theme.CultureDTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun MapScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MapViewModel = hiltViewModel()
) {
    MapScreen(
        modifier = modifier,
        navController = navController,
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: MapState,
    onAction: (MapAction) -> Unit
) {
    Scaffold() { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            Text(text = "Welcome to map screen")
        }
    }
}

@PreviewLightDark
@Composable
private fun MapScreenPreview() {
    CultureDTheme {
        MapScreen(
            navController = rememberNavController(),
            state = MapState(),
            onAction = {}
        )
    }
}