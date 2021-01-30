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

class MainActivityViewModel(
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _event = MutableSharedFlow<NavigationEvent>()
    val event: SharedFlow<NavigationEvent> = _event

    fun launchDetails(id: String) = viewModelScope.launch(mainDispatcher) {
        _event.emit(NavigationEvent.NavigateToDetails(id))
    }

    class Factory @Inject constructor(
        @MainDispatcher private val mainDispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = MainActivityViewModel(mainDispatcher) as T
    }
}

sealed class NavigationEvent {
    data class NavigateToDetails(val id: String) : NavigationEvent()
}