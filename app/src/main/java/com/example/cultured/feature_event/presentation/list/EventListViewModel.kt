package com.example.cultured.feature_event.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cultured.feature_event.data.model.EventModel
import com.example.cultured.feature_event.data.model.toEventUiModel
import com.example.cultured.feature_event.domain.repository.EventRepository
import com.example.cultured.feature_event.presentation.model.isHappeningAt
import com.example.cultured.util.DateUtil.TODAY_DATE
import com.example.cultured.util.DateUtil.getNDaysAgo
import com.google.firebase.auth.FirebaseAuth
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
    private val repository: EventRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _state = MutableStateFlow(EventListState())
    val state = _state
        .onStart {
            initState()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            EventListState()
        )

    private fun initState() {
        for (i in 0..365) {

            val eventCall = repository.getEventApi().getEventModelWithDate(TODAY_DATE.getNDaysAgo(i))
            eventCall.enqueue(object : Callback<EventModel> {
                override fun onResponse(eventModelCall: Call<EventModel>, response: Response<EventModel>) {
                    if (response.isSuccessful) {
                        try {
                            val eventUiModelSet = _state.value.entireEventUiModelSet.toMutableSet()
                            response.body()!!.eventList.map { event -> event.toEventUiModel() }
                                .forEach { eventUiModel ->
                                    if (eventUiModel.isHappeningAt(TODAY_DATE)) {
                                        eventUiModelSet.add(eventUiModel)
                                    }
                                }

                            _state.update {
                                it.copy(
                                    entireEventUiModelSet = eventUiModelSet.sortedByDescending { eventUiModel -> eventUiModel.startDate }
                                        .toSet(),
                                    displayingEventUiModelSet = _state.value.entireEventUiModelSet
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
    }

    fun onAction(action: EventListAction) {
        when (action) {
            is EventListAction.OnSearchQueryChange -> {
                onSearchQueryChange(action.searchQuery)
            }

            is EventListAction.OnLogoutClick -> {
                onLogoutClick()
            }
        }
    }

    private fun onLogoutClick() {
        firebaseAuth.signOut()
    }

    private fun onSearchQueryChange(searchQuery: String) {

        _state.update {
            it.copy(
                searchQuery = searchQuery
            )
        }

        val entireEventUiModel = state.value.entireEventUiModelSet
        _state.update {
            it.copy(
                displayingEventUiModelSet = entireEventUiModel.filter { eventUiModel ->
                    eventUiModel.title.contains(
                        searchQuery,
                        ignoreCase = true
                    )
                }.toSet()
            )
        }

    }
}