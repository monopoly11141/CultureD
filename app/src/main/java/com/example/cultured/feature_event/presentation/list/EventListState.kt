package com.example.cultured.feature_event.presentation.list

import com.example.cultured.feature_event.data.model.EventModel
import com.example.cultured.feature_event.presentation.model.EventUiModel

data class EventListState(
    val eventModel: EventModel = EventModel(),
    val eventUiModelList: List<EventUiModel> = emptyList()
)
