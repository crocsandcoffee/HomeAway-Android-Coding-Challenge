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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchFragmentViewModel(
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val repository: SearchVenueRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Success())
    val state: StateFlow<SearchState> = _state

    // TODO: cancel a previous search?
    fun query(searchTerm: String) {

        viewModelScope.launch(mainDispatcher) {

            _state.emit(SearchState.Loading)

            when (val result = repository.searchVenues(searchTerm)) {
                is VenueSearchResult.Success -> {
                    _state.value = SearchState.Success(items = map(result.venues))
                }
                is VenueSearchResult.Error -> {
                    _state.value = SearchState.Error
                }
            }
        }
    }

    private suspend fun map(venues: List<Venue>) = withContext(defaultDispatcher) {

        venues.map { venue ->
            var category: Category? = null

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

sealed class SearchState {
    object Loading : SearchState()
    object Error : SearchState()
    data class Success(val items: List<VenueListItem> = emptyList()) : SearchState()
}

data class VenueListItem(
    val id: String,
    val name: String,
    val category: String?,
    val distance: String?,
    val iconUri: Uri?
) {

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VenueListItem>() {

            override fun areItemsTheSame(oldItem: VenueListItem, newItem: VenueListItem) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: VenueListItem, newItem: VenueListItem) = oldItem == newItem
        }
    }
}