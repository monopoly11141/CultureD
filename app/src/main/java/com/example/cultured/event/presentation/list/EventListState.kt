package com.example.cultured.event.presentation.list

import com.example.cultured.event.presentation.model.EventUiModel

data class EventListState(
    val eventUiModelList: List<EventUiModel> = emptyList()
)
