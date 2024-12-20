package com.example.cultured.feature_event.presentation.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.core.presentation.model.changeFavoriteStatus
import com.example.cultured.core.presentation.model.isHappeningAt
import com.example.cultured.core.presentation.model.isStartedAt
import com.example.cultured.core.presentation.model.toDocumentId
import com.example.cultured.feature_event.data.model.toEventUiModel
import com.example.cultured.feature_event.domain.repository.EventRepository
import com.example.cultured.feature_event.presentation.model.NavigationItem
import com.example.cultured.feature_login.domain.FirebaseAuthError
import com.example.cultured.feature_login.presentation.login.LoginEvent
import com.example.cultured.util.DateUtil.TODAY_DATE
import com.example.cultured.util.DateUtil.getNDaysAgo
import com.example.cultured.util.EventTypeUtil.EVERY_EVENT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val repository: EventRepository,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _state = MutableStateFlow(
        EventListState(
            searchTypeSet = setOf(EVERY_EVENT),
            currentSearchType = EVERY_EVENT,
            selectedDisplay = NavigationItem.HOME.route,
        )
    )
    val state = _state
        .onStart {
            repeat(7) {
                onGetMoreEventUiModel()

                incrementDayBefore()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            EventListState()
        )

    private val _eventChannel = Channel<EventListEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private var gotData by mutableIntStateOf(0)

    private fun incrementDayBefore() {
        _state.update {
            it.copy(
                dayBefore = _state.value.dayBefore.plus(1)
            )
        }
    }

    fun onAction(action: EventListAction) {
        when (action) {
            EventListAction.OnGetMoreEventUiModel -> {
                onGetMoreEventUiModel()
                incrementDayBefore()
            }

            is EventListAction.OnSearchQueryChange -> {
                onSearchQueryChange(action.searchQuery)
            }

            is EventListAction.OnTypeClick -> {
                onTypeClick(action.type)
            }

            is EventListAction.OnItemFavoriteClick -> {
                onItemFavoriteClick(action.eventUiModel)
            }

            is EventListAction.OnItemShareClick -> {
                onItemShareClick(action.eventUiModel)
            }

            is EventListAction.OnLogoutClick -> {
                onLogoutClick()
            }

            is EventListAction.OnNavigationBarClick -> {
                onNavigationBarClick(action.navigationItem)
            }

        }
    }

    private fun onItemShareClick(eventUiModel: EventUiModel) {
        viewModelScope.launch {
            _eventChannel.send(EventListEvent.OnShare(eventUiModel))
        }
    }

    private fun onGetMoreEventUiModel() {

        val interestedDate = TODAY_DATE.getNDaysAgo(_state.value.dayBefore)
        gotData = 0

        viewModelScope.launch {
            val eventModel = repository.getEventApi().getEventModelWithDate(interestedDate)

            eventModel.eventList
                .map { event -> event.toEventUiModel() }
                .forEach { eventUiModel ->

                    if (!eventUiModel.isStartedAt(interestedDate) or !eventUiModel.isHappeningAt(
                            TODAY_DATE
                        )
                    ) {
                        return@forEach
                    }

                    //from here, data exists
                    gotData++
                    var thisEventUiModel = eventUiModel

                    firestore
                        .collection(firebaseAuth.currentUser!!.uid)
                        .document(thisEventUiModel.toDocumentId())
                        .get()
                        .addOnCompleteListener { task ->
                            val document = task.result
                            if (task.isSuccessful) {
                                if (document != null) {
                                    if (document.exists()) {
                                        thisEventUiModel = thisEventUiModel.changeFavoriteStatus()
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
                            searchTypeSet = _state.value.searchTypeSet.plus(
                                eventUiModel.typeList.toList()
                            )
                                .sortedWith(
                                    compareByDescending<String> { type -> type == EVERY_EVENT }
                                        .thenBy { type -> type })
                                .toSet(),
                        )
                    }

                }
        }.invokeOnCompletion {
            if (gotData == 0 && state.value.dayBefore <= 365) {
                incrementDayBefore()
                viewModelScope.launch {
                    onGetMoreEventUiModel()
                }
            }
        }

    }

    private fun onNavigationBarClick(navigationItem: NavigationItem) {
        when (navigationItem) {

            NavigationItem.HOME -> {
                _state.update {
                    it.copy(
                        searchQuery = "",
                        displayingEventUiModelSet = state.value.entireEventUiModelSet,
                        selectedDisplay = navigationItem.route
                    )
                }
            }

            NavigationItem.FAVORITE -> {

                _state.update {
                    it.copy(
                        searchQuery = "",
                        displayingEventUiModelSet = emptySet(),
                        selectedDisplay = navigationItem.route
                    )
                }

                firestore.collection(firebaseAuth.currentUser?.uid ?: "")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            val thisEventUiModel =
                                document.toObject(EventUiModel::class.java).changeFavoriteStatus()
                            _state.update {
                                it.copy(
                                    displayingEventUiModelSet = _state.value.displayingEventUiModelSet.plus(
                                        thisEventUiModel
                                    )
                                )
                            }
                        }
                    }
                    .addOnFailureListener { exception ->

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
                searchQuery = "",
                currentSearchType = type
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
        updateUiOnItemFavoriteClick(eventUiModel)

        updateFirestoreDbOnItemFavoriteClick(eventUiModel)
    }

    private fun updateUiOnItemFavoriteClick(eventUiModel: EventUiModel) {
        var entireEventUiModelSet = _state.value.entireEventUiModelSet.toMutableSet()
        var displayingEventUiModelSet = _state.value.displayingEventUiModelSet.toMutableSet()

        val indexInEntireEventUiModelSet = entireEventUiModelSet.indexOf(eventUiModel)

        if(indexInEntireEventUiModelSet != -1) {
            entireEventUiModelSet = entireEventUiModelSet.toMutableList().apply {
                this[indexInEntireEventUiModelSet] = eventUiModel.changeFavoriteStatus()
            }.toMutableSet()
        }

        val indexInDisplayingEventUiModelSet = displayingEventUiModelSet.indexOf(eventUiModel)

        if(indexInDisplayingEventUiModelSet != -1) {
            displayingEventUiModelSet = displayingEventUiModelSet.toMutableList().apply {
                this[indexInDisplayingEventUiModelSet] = eventUiModel.changeFavoriteStatus()
            }.toMutableSet()
        }

        _state.update {
            it.copy(
                entireEventUiModelSet = entireEventUiModelSet,
                displayingEventUiModelSet = displayingEventUiModelSet
            )
        }
    }

    private fun updateFirestoreDbOnItemFavoriteClick(eventUiModel: EventUiModel) {
        val firestoreDocumentId = eventUiModel.toDocumentId()

        when (!eventUiModel.isFavorite) {
            true -> {
                firestore
                    .collection(firebaseAuth.currentUser!!.uid)
                    .document(firestoreDocumentId)
                    .set(eventUiModel)
                    .addOnSuccessListener { documentReference ->
                        Log.d("EventListViewModel", "Added with id: $documentReference")
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