package com.example.cultured.feature_event.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.core.presentation.model.isHappeningAt
import com.example.cultured.core.presentation.model.toSha245EncodedString
import com.example.cultured.feature_event.data.model.EventModel
import com.example.cultured.feature_event.data.model.toEventUiModel
import com.example.cultured.feature_event.domain.repository.EventRepository
import com.example.cultured.feature_event.presentation.model.NavigationItem
import com.example.cultured.util.DateUtil.TODAY_DATE
import com.example.cultured.util.DateUtil.getNDaysAgo
import com.example.cultured.util.EventTypeUtil.EVERY_EVENT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val repository: EventRepository,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _state = MutableStateFlow(EventListState(searchTypeSet = setOf(EVERY_EVENT)))
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
                    if (!response.isSuccessful) {
                        throw Error("Error happened")
                    }

                    try {
                        response.body()?.let { body ->
                            body.eventList.map { event -> event.toEventUiModel() }
                                .forEach { eventUiModel ->

                                    if (!eventUiModel.isHappeningAt(TODAY_DATE)) {
                                        return@forEach
                                    }

                                    var thisEventUiModel = eventUiModel

                                    viewModelScope.launch {
                                        firestore
                                            .collection(firebaseAuth.currentUser!!.uid)
                                            .document(thisEventUiModel.toSha245EncodedString())
                                            .get()
                                            .addOnCompleteListener { task ->
                                                val document = task.result
                                                if (task.isSuccessful) {
                                                    if (document != null) {
                                                        if (document.exists()) {
                                                            thisEventUiModel = thisEventUiModel.copy(
                                                                isFavorite = true
                                                            )
                                                        }
                                                    }
                                                }

                                            }.await()

                                        _state.update {
                                            it.copy(
                                                entireEventUiModelSet = _state.value.entireEventUiModelSet.plus(
                                                    thisEventUiModel
                                                )
                                                    .sortedWith(compareByDescending<EventUiModel> { eventUiModel -> eventUiModel.startDate }
                                                        .thenBy { eventUiModel -> eventUiModel.title })
                                                    .toSet(),
                                                displayingEventUiModelSet = _state.value.entireEventUiModelSet,
                                                searchTypeSet = _state.value.searchTypeSet.plus(eventUiModel.typeList.toList())
                                            )
                                        }
                                    }
                                }

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
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

            is EventListAction.OnTypeClick -> {
                onTypeClick(action.type)
            }

            is EventListAction.OnItemFavoriteClick -> {
                onItemFavoriteClick(action.eventUiModel)
            }

            is EventListAction.OnLogoutClick -> {
                onLogoutClick()
            }

            is EventListAction.OnNavigationBarClick -> {
                onNavigationBarClick(action.navigationItem)
            }
        }
    }

    private fun onNavigationBarClick(navigationItem: NavigationItem) {
        when (navigationItem) {

            NavigationItem.HOME -> {
                _state.update {
                    it.copy(
                        searchQuery = "",
                        displayingEventUiModelSet = state.value.entireEventUiModelSet
                    )
                }
            }

            NavigationItem.FAVORITE -> {
                _state.update {
                    it.copy(
                        searchQuery = "",
                        displayingEventUiModelSet = state.value.entireEventUiModelSet.filter { eventUiModel ->
                            eventUiModel.isFavorite
                        }.toSet()
                    )
                }
            }
        }
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

    private fun onTypeClick(type: String) {
        _state.update {
            it.copy(
                searchQuery = ""
            )
        }

        val displayingEventUiModel = if (type == EVERY_EVENT) {
            _state.value.entireEventUiModelSet
        } else {
            _state.value.entireEventUiModelSet.filter { eventUiModel ->
                eventUiModel.typeList.contains(
                    type
                )
            }.toSet()
        }

        _state.update {
            it.copy(
                displayingEventUiModelSet = displayingEventUiModel
            )
        }
    }

    private fun onItemFavoriteClick(eventUiModel: EventUiModel) {
        var entireEventUiModelSet = _state.value.entireEventUiModelSet.toMutableSet()
        var displayingEventUiModelSet = _state.value.displayingEventUiModelSet.toMutableSet()

        lateinit var foundEventUiModel: EventUiModel


        entireEventUiModelSet = entireEventUiModelSet.map {
            if (it == eventUiModel) {
                foundEventUiModel = it
                foundEventUiModel = foundEventUiModel.copy(
                    isFavorite = !foundEventUiModel.isFavorite
                )
                foundEventUiModel
            } else {
                it
            }
        }.toMutableSet()

        displayingEventUiModelSet = displayingEventUiModelSet.map {
            if (it == eventUiModel) {
                foundEventUiModel
            } else {
                it
            }
        }.toMutableSet()

        _state.update {
            it.copy(
                entireEventUiModelSet = entireEventUiModelSet,
                displayingEventUiModelSet = displayingEventUiModelSet
            )
        }
        val firestoreDocumentId = foundEventUiModel.copy(
            isFavorite = false
        ).toSha245EncodedString()

        when (foundEventUiModel.isFavorite) {
            true -> {
                firestore
                    .collection(firebaseAuth.currentUser!!.uid)
                    .document(firestoreDocumentId)
                    .set(foundEventUiModel)
                    .addOnSuccessListener { documentReference ->
                        Log.d("EventListViewModel", "Added with id: ${documentReference}")
                    }
                    .addOnFailureListener { e ->
                        Log.d("EventListViewModel", "Error :  $e")
                    }
            }

            false -> {
                firestore
                    .collection(firebaseAuth.currentUser!!.uid)
                    .document(firestoreDocumentId)
                    .delete()
            }
        }
    }


    private fun onLogoutClick() {
        firebaseAuth.signOut()
    }

}