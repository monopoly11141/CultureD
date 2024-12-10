package com.example.cultured.feature_event.presentation.list.component

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EventTagFlowRow(
    modifier: Modifier = Modifier,
    searchType: Set<String>,
    onTypeClick: (String) -> Unit
) {
    FlowRow(
        modifier = modifier
    ) {
        searchType.forEach { searchType ->
            EventTypeItem(
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                typeString = searchType,
                fontSize = 14.sp,
                onClick = { onTypeClick(searchType) }
            )
        }
    }
}