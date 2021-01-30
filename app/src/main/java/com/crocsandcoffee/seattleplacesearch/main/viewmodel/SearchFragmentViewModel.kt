package com.crocsandcoffee.seattleplacesearch.main.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.crocsandcoffee.seattleplacesearch.dagger.DefaultDispatcher
import com.crocsandcoffee.seattleplacesearch.dagger.MainDispatcher
import com.crocsandcoffee.seattleplacesearch.main.model.Category
import com.crocsandcoffee.seattleplacesearch.main.model.Venue
import com.crocsandcoffee.seattleplacesearch.main.model.VenueSearchResult
import com.crocsandcoffee.seattleplacesearch.main.repository.SearchVenueRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Omid
 *
 * [ViewModel] for [SearchFragment]
 *
 * Responsible for passing the user's searchTerm ([query]) to the [SearchVenueRepository]
 * and mapping the backend model to the ui model passed to the list adapter.
 *
 * This ViewModel emits a single [state] which indicates the UI state, which will be
 * ane of [SearchUiState.Error] or [SearchUiState.Success] at any given point in time.
 */
class SearchFragmentViewModel(
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val repository: SearchVenueRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SearchUiState>(SearchUiState.Success())
    val state: StateFlow<SearchUiState> = _state

    private var job: Job? = null

    /**
     * Query the list of venues given the [searchTerm]
     */
    fun query(searchTerm: String) {

         // Cancel the previous job/query if running
        job?.cancel()
        job = viewModelScope.launch(mainDispatcher) {

            // if empty, return empty list so the UI updates
            if (searchTerm.isEmpty()) {
                _state.value = SearchUiState.Success(emptyList())
                return@launch
            }

            when (val result = repository.searchVenues(searchTerm)) {
                is VenueSearchResult.Success -> {
                    _state.value = SearchUiState.Success(items = map(result.venues))
                }
                is VenueSearchResult.Error -> {
                    _state.value = SearchUiState.Error
                }
            }
        }
    }

    /**
     * Map the list of [venues] to a list of [VenueListItem]
     */
    private suspend fun map(venues: List<Venue>) = withContext(defaultDispatcher) {

        venues.map { venue ->

            var category: Category? = null

            /**
             * If categories is not empty, we should try to find the first category that is primary
             * to give us the main category name, if there is none, just default to the first category
             */
            if (venue.categories.isNotEmpty()) {
                category = venue.categories.firstOrNull { it.primary } ?: venue.categories.first()
            }

            VenueListItem(
                id = venue.id,
                name = venue.name,
                iconUri = category?.iconUri,
                category = category?.name,
                distance = venue.location?.distance?.toString()
            )
        }
    }

    class Factory @Inject constructor(
        @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
        @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
        private val repository: SearchVenueRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            @Suppress("UNCHECKED_CAST")
            return SearchFragmentViewModel(mainDispatcher, defaultDispatcher, repository) as T
        }
    }

}

/**
 *  Sealed class hierarchy for encapsulating the different UI States for the [SearchFragment]
 */
sealed class SearchUiState {

    // indicates an error has occurred
    object Error : SearchUiState()

    // indicates a state with data to render: the list of venues
    data class Success(val items: List<VenueListItem> = emptyList()) : SearchUiState()
}

/**
 * UI Model displayed in our List Adapter
 */
data class VenueListItem(
    val id: String,
    val name: String,
    val category: String?,
    val distance: String?,
    val iconUri: Uri?
) {

    companion object {

        /**
         * A [DiffUtil.ItemCallback] used to determine item diffs when animating/updating list items
         */
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VenueListItem>() {

            override fun areItemsTheSame(oldItem: VenueListItem, newItem: VenueListItem) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: VenueListItem, newItem: VenueListItem) = oldItem == newItem
        }
    }
}