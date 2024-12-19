package com.example.cultured.feature_map.presentation.map

sealed interface MapAction {
    data object Init: MapAction
}