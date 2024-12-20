package com.example.cultured.feature_event.presentation.list

import com.example.cultured.core.presentation.model.EventUiModel

data class EventListState(
    val entireEventUiModelSet: Set<EventUiModel> = emptySet(),
    val displayingEventUiModelSet: Set<EventUiModel> = emptySet(),
    val searchQuery: String = "",
    val searchTypeSet: Set<String> = emptySet(),
    val currentSearchType: String = "",
    val selectedDisplay: String = "",
    val dayBefore : Int = 0
)