package com.example.cultured.feature_event.presentation.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.feature_event.presentation.list.component.EventItem
import com.example.cultured.feature_event.presentation.model.EventUiModel
import com.example.cultured.feature_event.presentation.preview.EventUiModelListProvider
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
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun EventListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: EventListState,
    onAction: (EventListAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(state.eventUiModelSet.toList()) { eventUiModel ->
            EventItem(
                eventUiModel = eventUiModel,
                onItemClick = {}
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun EventListScreenPreview(@PreviewParameter(EventUiModelListProvider::class) eventUiModelList: List<EventUiModel>) {
    CultureDTheme {
        EventListScreen(
            navController = rememberNavController(),
            state = EventListState(
                eventUiModelSet = eventUiModelList.toSet()
            ),
            onAction = {}
        )
    }
}