package com.crocsandcoffee.seattleplacesearch.main.repository

import com.crocsandcoffee.seattleplacesearch.main.api.FourSquareService
import com.crocsandcoffee.seattleplacesearch.main.model.VenueSearchResult
import com.crocsandcoffee.seattleplacesearch.main.model.WTFException
import javax.inject.Inject


private const val CLIENT_ID = ""
private const val CLIENT_SECRET = ""

class SearchVenueRepository @Inject constructor(private val service: FourSquareService) {

    suspend fun searchVenues(term: String): VenueSearchResult {

        return try {

            service.searchVenues(
                clientId = CLIENT_ID,
                clientSecret = CLIENT_SECRET,
                searchTerm = term
            ).response?.venues?.let { venues -> VenueSearchResult.Success(venues) }
                ?: VenueSearchResult.Error(WTFException())

        } catch (e: Throwable) {
            VenueSearchResult.Error(e)
        }
    }
}