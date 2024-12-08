package com.example.cultured.feature_event.presentation.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cultured.R
import com.example.cultured.feature_event.presentation.list.component.EventItem
import com.example.cultured.feature_event.presentation.list.component.EventTypeItem
import com.example.cultured.feature_event.presentation.model.EventUiModel
import com.example.cultured.feature_event.presentation.preview.EventUiModelListProvider
import com.example.cultured.navigation.Screen
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EventListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: EventListState,
    onAction: (EventListAction) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            Column(

            ) {
                TopAppBar(
                    modifier = modifier
                        .heightIn(max = 56.dp),
                    title = { Text(text = "행사 정보") },
                    actions = {
                        IconButton(onClick = {
                            onAction.invoke(EventListAction.OnLogoutClick)
                            navController.navigate(Screen.LoginScreen.route)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.signout),
                                contentDescription = "로그아웃",
                                modifier = Modifier
                                    .scale(0.7f)
                            )
                        }
                    }
                )

                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 4.dp),
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = state.searchQuery,
                            onQueryChange = { searchQuery ->
                                onAction(EventListAction.OnSearchQueryChange(searchQuery))
                            },
                            onSearch = { searchQuery ->
                                onAction(EventListAction.OnSearchQueryChange(searchQuery))
                            },
                            expanded = false,
                            onExpandedChange = {},
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = "검색 아이콘"
                                )
                            }
                        )
                    },
                    expanded = false,
                    onExpandedChange = {}
                ) {}

                FlowRow(
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    state.searchTypeSet.forEach { searchType ->
                        EventTypeItem(
                            modifier = Modifier
                                .padding(horizontal = 4.dp),
                            typeString = searchType,
                            fontSize = 14.sp,
                            onClick = { onAction.invoke(EventListAction.OnTypeClick(searchType)) }
                        )
                    }
                }

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
                        uriHandler.openUri(eventUiModel.eventUrl)
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