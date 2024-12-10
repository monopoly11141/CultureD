package com.example.cultured.feature_event.presentation.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cultured.feature_event.presentation.list.EventListAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventSearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick : (String) -> Unit,

) {
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp),
        inputField = {
            SearchBarDefaults.InputField(
                query = searchQuery,
                onQueryChange = { searchQuery ->
                    onSearchQueryChange(searchQuery)
//                    onAction(EventListAction.OnSearchQueryChange(searchQuery))
                },
                onSearch = { searchQuery ->
                    onSearchClick(searchQuery)
                    //onAction(EventListAction.OnSearchQueryChange(searchQuery))
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
}