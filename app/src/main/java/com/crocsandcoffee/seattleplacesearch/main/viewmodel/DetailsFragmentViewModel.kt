package com.crocsandcoffee.seattleplacesearch.main.viewmodel

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crocsandcoffee.seattleplacesearch.common.SavedStateFactory
import com.crocsandcoffee.seattleplacesearch.dagger.DefaultDispatcher
import com.crocsandcoffee.seattleplacesearch.dagger.MainDispatcher
import com.crocsandcoffee.seattleplacesearch.dagger.MapsApiKey
import com.crocsandcoffee.seattleplacesearch.main.api.SEATTLE_WASHINGTON_CENTER
import com.crocsandcoffee.seattleplacesearch.main.model.Category
import com.crocsandcoffee.seattleplacesearch.main.model.VenueDetails
import com.crocsandcoffee.seattleplacesearch.main.model.VenueDetailsResult
import com.crocsandcoffee.seattleplacesearch.main.repository.SearchVenueRepository
import com.crocsandcoffee.seattleplacesearch.main.repository.VenueDetailRepository
import com.crocsandcoffee.seattleplacesearch.main.ui.fragment.DetailsFragment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val BASE_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?center=${SEATTLE_WASHINGTON_CENTER}&zoom=14&size=400x400"
private const val SEATTLE_WASHINGTON_MARKER = "&markers=color:green|${SEATTLE_WASHINGTON_CENTER}"
private const val VENUE_MARKER_BASE = "&markers=color:red|"

/**
 * @author Omid
 *
 * [ViewModel] for [DetailsFragment]
 *
 * Responsible for loading details about a venue via [venueId] from [VenueDetailRepository]
 * and mapping the backend model to our Ui model.
 *
 * This ViewModel emits a single [state] which indicates the UI state.
 *
 * A [savedStateHandle] is passed to this VM so the [venueId] can be retrieved and can be
 * persisted in case this [ViewModel] gets destroyed and recreated by the system
 */
class DetailsFragmentViewModel(
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val repository: VenueDetailRepository,
    private val apiKey: String,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val state: StateFlow<DetailsUiState> = _state

    private val venueId: String = savedStateHandle[DetailsFragment.VENUE_ID_KEY]
        ?: throw IllegalStateException("A venueId is required")

    fun loadDetails() {

        viewModelScope.launch(mainDispatcher) {

            _state.value = DetailsUiState.Loading

            when (val result = repository.getVenueDetails(venueId)) {
                is VenueDetailsResult.Success -> {
                    _state.value = map(result.details)
                }
                is VenueDetailsResult.Error -> {
                    _state.value = DetailsUiState.Error
                }
            }
        }
    }

    private suspend fun map(details: VenueDetails) = withContext(defaultDispatcher) {

        var category: Category? = null

        /**
         * If categories is not empty, we should try to find the first category that is primary
         * to give us the main category name, if there is none, just default to the first category
         */
        if (details.categories.isNotEmpty()) {
            category = details.categories.firstOrNull { it.primary } ?: details.categories.first()
        }

        DetailsUiState.Success(
            venueName = details.name,
            isVenueVerified = details.verified,
            venueUrl = details.url ?: "",
            likes = details.likes?.summary ?: "",
            bestPhoto = details.bestPhoto?.photoUrI ?: Uri.EMPTY,
            contact = details.contact?.let { it.formattedPhone ?: it.twitter ?: it.phone },
            address = details.location?.address,
            city = details.location?.city,
            state = details.location?.state,
            postalCode = details.location?.postalCode,
            country = details.location?.country,
            categoryName = category?.name ?: "",
            categoryIconUri = category?.iconUri ?: Uri.EMPTY,
            mapUrl = buildMapUrl(details.location?.lat, details.location?.long).toUri()
        )
    }

    private fun buildMapUrl(lat: Double?, long: Double?): String {

        // if either of these coordinates are null, just default to the map of seattle
        return if (lat == null || long == null) {
            "${BASE_MAP_URL}${SEATTLE_WASHINGTON_MARKER}&key=$apiKey"
        } else {
            "${BASE_MAP_URL}${SEATTLE_WASHINGTON_MARKER}${VENUE_MARKER_BASE}${lat},${long}&key=$apiKey"
        }
    }

    class Factory @Inject constructor(
        @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
        @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
        private val repository: VenueDetailRepository,
        @MapsApiKey private val apiKey: String
    ) : SavedStateFactory<DetailsFragmentViewModel> {

        override fun create(handle: SavedStateHandle): DetailsFragmentViewModel {
            return DetailsFragmentViewModel(
                mainDispatcher = mainDispatcher,
                defaultDispatcher = defaultDispatcher,
                repository = repository,
                apiKey = apiKey,
                savedStateHandle = handle
            )
        }
    }
}

/**
 *  Sealed class hierarchy for encapsulating the different UI States for the [DetailsFragment]
 */
sealed class DetailsUiState {

    // indicates loading of details from the backend
    object Loading : DetailsUiState()

    // indicates an error has occurred
    object Error : DetailsUiState()

    // indicates a state with data to render: the list of venues
    data class Success(
        val venueName: String,
        val isVenueVerified: Boolean,
        val venueUrl: String,
        val likes: String,
        val bestPhoto: Uri,
        val contact: String?,
        val address: String?,
        val city: String?,
        val state: String?,
        val postalCode: String?,
        val country: String?,
        val mapUrl: Uri,
        val categoryName: String,
        val categoryIconUri: Uri
    ) : DetailsUiState()
}