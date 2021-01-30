package com.crocsandcoffee.seattleplacesearch.main.repository

import com.crocsandcoffee.seattleplacesearch.dagger.ClientID
import com.crocsandcoffee.seattleplacesearch.dagger.ClientSecret
import com.crocsandcoffee.seattleplacesearch.main.api.FourSquareService
import com.crocsandcoffee.seattleplacesearch.main.model.VenueSearchResult
import com.crocsandcoffee.seattleplacesearch.main.model.WTFException
import javax.inject.Inject

/**
 * @author Omid
 *
 * Repository responsible for fetching list of venues from [FourSquareService]
 */
class SearchVenueRepository @Inject constructor(
    private val service: FourSquareService,
    @ClientID private val clientID: String,
    @ClientSecret private val clientSecret: String
) {

    /**
     * Download a list of venues given the user's search [term]
     */
    suspend fun searchVenues(term: String): VenueSearchResult {

        return try {
            service.searchVenues(
                clientId = clientID,
                clientSecret = clientSecret,
                searchTerm = term
            ).response?.venues?.let { venues -> VenueSearchResult.Success(venues) }
                ?: VenueSearchResult.Error(WTFException())

        } catch (e: Throwable) {
            VenueSearchResult.Error(e)
        }
    }
}