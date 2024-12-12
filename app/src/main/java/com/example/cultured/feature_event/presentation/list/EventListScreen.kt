package com.example.cultured.feature_event.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.feature_event.presentation.list.component.EventItem
import com.example.cultured.feature_event.presentation.list.component.EventSearchBar
import com.example.cultured.feature_event.presentation.list.component.EventTagFlowRow
import com.example.cultured.feature_event.presentation.list.component.EventTopAppBar
import com.example.cultured.feature_event.presentation.model.NavigationItem
import com.example.cultured.feature_event.presentation.preview.EventUiModelListProvider
import com.example.cultured.navigation.Screen
import com.example.cultured.ui.theme.CultureDTheme
import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
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

        },
        bottomBar = {
            NavigationBar(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                NavigationBarItem(
                    selected = state.selectedDisplay == "home",
                    onClick = { onAction.invoke(EventListAction.OnNavigationBarClick(NavigationItem.HOME)) },
                    icon = {
                        Icon(
                            Icons.Outlined.Home,
                            "home"
                        )
                    }
                )

                NavigationBarItem(
                    selected = state.selectedDisplay == "favorite",
                    onClick = { onAction.invoke(EventListAction.OnNavigationBarClick(NavigationItem.FAVORITE)) },
                    icon = {
                        Icon(
                            Icons.Outlined.FavoriteBorder,
                            "favorite"
                        )
                    }
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    }
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.primary
                )
            }
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
                entireEventUiModelSet = eventUiModelList.toSet(),
                displayingEventUiModelSet = eventUiModelList.toSet(),
                searchTypeSet = eventUiModelList.map { it -> it.typeList }.flatten().toSet()
            ),
            onAction = {}
        )
    }
}