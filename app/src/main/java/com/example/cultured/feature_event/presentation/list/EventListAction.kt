package com.example.cultured.feature_event.presentation.list

sealed interface EventListAction {
    data class OnSearchQueryChange(val searchQuery: String) : EventListAction
    data class OnTypeClick(val type: String) : EventListAction
    data object OnLogoutClick : EventListAction
}