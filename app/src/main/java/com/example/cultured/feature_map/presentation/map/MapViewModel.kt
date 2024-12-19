package com.example.cultured.feature_map.presentation.map

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(
        MapState()
    )
    val state = _state
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            MapState()
        )

    fun onAction(action: MapAction) {
        when (action) {
            MapAction.Init -> {
                init()
            }
        }
    }

    private fun init() {
        _state.update {
            it.copy(
                latitude = (savedStateHandle.get<String>("latitude") ?: "0.0").toDouble(),
                longitude = (savedStateHandle.get<String>("longitude") ?: "0.0").toDouble()
            )
        }
    }
}