package com.example.cultured.feature_event.presentation.list

import com.example.cultured.core.presentation.model.EventUiModel

sealed interface EventListEvent {
    data class OnShare(val eventUiModel: EventUiModel) : EventListEvent
}