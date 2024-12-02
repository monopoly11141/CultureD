package com.example.cultured.feature_event.presentation.list

import com.example.cultured.feature_event.presentation.model.EventUiModel

data class EventListState(
    val eventUiModelList: List<EventUiModel> = emptyList()
)
