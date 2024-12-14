package com.example.cultured.feature_event.presentation.list

import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.feature_event.presentation.model.NavigationItem

sealed interface EventListAction {
    data object OnGetMoreEventUiModel : EventListAction
    data class OnSearchQueryChange(val searchQuery: String) : EventListAction
    data class OnTypeClick(val type: String) : EventListAction
    data class OnItemFavoriteClick(val eventUiModel: EventUiModel) : EventListAction
    data class OnNavigationBarClick(val navigationItem: NavigationItem): EventListAction
    data object OnLogoutClick : EventListAction
}