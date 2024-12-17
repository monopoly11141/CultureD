package com.example.cultured.feature_event.presentation.list

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.core.presentation.preview.PreviewModel
import com.example.cultured.core.presentation.preview.PreviewParameterProvider
import com.example.cultured.core.presentation.util.ObserveAsEvents
import com.example.cultured.feature_event.presentation.list.component.EventItem
import com.example.cultured.feature_event.presentation.list.component.EventSearchBar
import com.example.cultured.feature_event.presentation.list.component.EventTagFlowRow
import com.example.cultured.feature_event.presentation.list.component.EventTopAppBar
import com.example.cultured.feature_event.presentation.model.NavigationItem
import com.example.cultured.navigation.Screen
import com.example.cultured.ui.theme.CultureDTheme
import com.example.cultured.util.EventTypeUtil.EVERY_EVENT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

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
        eventFlow = viewModel.eventChannel,
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
    eventFlow: Flow<EventListEvent>,
    onAction: (EventListAction) -> Unit
) {
    val context = LocalContext.current

    ObserveAsEvents(eventFlow = eventFlow) { event ->
        when (event) {
            is EventListEvent.OnShare -> {

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "${event.eventUiModel.title}\n " + "${event.eventUiModel.eventUrl}"
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            Column(

            ) {
                EventTopAppBar(
                    modifier = modifier
                        .heightIn(max = 56.dp),
                    titleText = "행사 정보"
                ) {
                    onAction.invoke(EventListAction.OnLogoutClick)
                    navController.navigate(Screen.LoginScreen.route)
                }

                EventSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 4.dp),
                    searchQuery = state.searchQuery,
                    onSearchQueryChange = { searchQuery ->
                        onAction(EventListAction.OnSearchQueryChange(searchQuery))
                    },
                    onSearchClick = { searchQuery ->
                        onAction(EventListAction.OnSearchQueryChange(searchQuery))
                    }
                )

                EventTagFlowRow(
                    modifier = modifier
                        .fillMaxWidth(),
                    searchType = state.searchTypeSet,
                    onTypeClick = { type ->
                        onAction.invoke(EventListAction.OnTypeClick(type))
                    }
                )

            }
        }
    ) { paddingValues ->

        val lazyColumnState = rememberLazyListState()
        val isAtBottom = !lazyColumnState.canScrollForward
        LaunchedEffect(isAtBottom && state.dayBefore <= 365) {
            if (isAtBottom && state.selectedDisplay == NavigationItem.HOME.route && state.currentSearchType == EVERY_EVENT) {
                onAction.invoke(EventListAction.OnGetMoreEventUiModel)
            }
        }

        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = modifier
                    .weight(1f),
                state = lazyColumnState
            ) {
                items(state.displayingEventUiModelSet.toList()) { eventUiModel ->
                    EventItem(
                        eventUiModel = eventUiModel,
                        onItemClick = {
                            navController.navigate(eventUiModel)
                        },
                        onTypeClick = { typeItem ->
                            onAction.invoke(EventListAction.OnTypeClick(typeItem))
                        },
                        onFavoriteIconClick = {
                            onAction.invoke(EventListAction.OnItemFavoriteClick(eventUiModel))
                        },
                        onShareIconClick = {
                            onAction.invoke(EventListAction.OnItemShareClick(eventUiModel))
                        }
                    )

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onAction.invoke(EventListAction.OnNavigationBarClick(NavigationItem.HOME)) },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Icon(
                        Icons.Outlined.Home,
                        "home"
                    )
                }

                IconButton(
                    onClick = { onAction.invoke(EventListAction.OnNavigationBarClick(NavigationItem.FAVORITE)) },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        "favorite"
                    )
                }
            }
        }
    }

}

@PreviewLightDark
@Composable
private fun EventListScreenPreview(@PreviewParameter(PreviewParameterProvider::class) previewModel: PreviewModel) {
    CultureDTheme {
        EventListScreen(
            navController = rememberNavController(),
            state = EventListState(
                entireEventUiModelSet = previewModel.eventUiModelList.toSet(),
                displayingEventUiModelSet = previewModel.eventUiModelList.toSet(),
                searchTypeSet = previewModel.eventUiModelList.map { it -> it.typeList }.flatten().toSet()
            ),
            eventFlow = emptyFlow(),
            onAction = {},
        )
    }
}