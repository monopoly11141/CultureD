package com.example.cultured.feature_event.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cultured.feature_event.data.model.EventModel
import com.example.cultured.feature_event.data.model.toEventUiModel
import com.example.cultured.feature_event.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EventListState())
    val state = _state
        .onStart {
            val eventCall = repository.getEventApi().getEventModel("2024-10-10")

            eventCall.enqueue(object : Callback<EventModel> {
                override fun onResponse(eventModelCall: Call<EventModel>, response: Response<EventModel>) {
                    if (response.isSuccessful) {
                        try {
                            _state.update {
                                it.copy(
                                    eventUiModelList = response.body()!!.eventList.map{event -> event.toEventUiModel()},
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(eventModelCall: Call<EventModel>, throwable: Throwable) {
                    throwable.printStackTrace()
                }

            })
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            EventListState()
        )

    fun onAction(action: EventListAction) {
        when (action) {
            else -> {}
        }
    }
}