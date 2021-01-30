package com.crocsandcoffee.seattleplacesearch.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crocsandcoffee.seattleplacesearch.dagger.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Omid
 *
 * [ViewModel] for [MainActivity] that handles navigation events
 *
 * A [SharedFlow] is emitted, which acts like a Event Bus. See [_event]
 */
class MainActivityViewModel(
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _event = MutableSharedFlow<NavigationEvent>()
    val event: SharedFlow<NavigationEvent> = _event

    /**
     * Launch the Venue Details Screen
     */
    fun launchDetails(venueId: String) = viewModelScope.launch(mainDispatcher) {
        _event.emit(NavigationEvent.NavigateToDetails(venueId))
    }

    class Factory @Inject constructor(
        @MainDispatcher private val mainDispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = MainActivityViewModel(mainDispatcher) as T
    }
}

/**
 * Represents the possible Navigation events supported in this flow
 */
sealed class NavigationEvent {
    data class NavigateToDetails(val id: String) : NavigationEvent()
}