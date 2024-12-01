package com.example.cultured.event.presentation.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(

): ViewModel() {

    fun onAction(action: EventListAction) {
        when(action) {
            else -> {}
        }
    }
}