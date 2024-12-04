package com.example.cultured.feature_event.presentation.list

import com.example.cultured.feature_event.presentation.model.EventUiModel

data class EventListState(
    val entireEventUiModelSet: Set<EventUiModel> = emptySet(),
    val displayingEventUiModelSet: Set<EventUiModel> = emptySet(),
    val searchQuery: String = ""
)
