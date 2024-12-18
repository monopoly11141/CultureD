package com.example.cultured.feature_map.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cultured.feature_event.presentation.list.EventListState
import com.example.cultured.feature_event.presentation.model.NavigationItem
import com.example.cultured.feature_login.presentation.login.LoginAction
import com.example.cultured.util.EventTypeUtil.EVERY_EVENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(
        MapState()
    )
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            MapState()
        )

    fun onAction(action: MapAction) {
        when (action) {
            else -> {}
        }
    }
}