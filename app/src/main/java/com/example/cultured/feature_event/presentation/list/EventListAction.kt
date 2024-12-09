package com.example.cultured.feature_event.presentation.list

import com.example.cultured.feature_event.presentation.model.EventUiModel

sealed interface EventListAction {
    data class OnSearchQueryChange(val searchQuery: String) : EventListAction
    data class OnTypeClick(val type: String) : EventListAction
    data class OnItemFavoriteClick(val eventUiModel: EventUiModel) : EventListAction
    data object OnLogoutClick : EventListAction
}