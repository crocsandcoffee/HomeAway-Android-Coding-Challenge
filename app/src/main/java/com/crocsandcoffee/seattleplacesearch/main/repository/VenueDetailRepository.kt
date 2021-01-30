package com.crocsandcoffee.seattleplacesearch.main.repository

import com.crocsandcoffee.seattleplacesearch.dagger.ClientID
import com.crocsandcoffee.seattleplacesearch.dagger.ClientSecret
import com.crocsandcoffee.seattleplacesearch.main.api.FourSquareService
import com.crocsandcoffee.seattleplacesearch.main.model.VenueDetailsResult
import com.crocsandcoffee.seattleplacesearch.main.model.WTFException
import javax.inject.Inject

/**
 * @author Omid
 *
 * Repository responsible for fetching details about a Venue from [FourSquareService]
 */
class VenueDetailRepository @Inject constructor(
    private val service: FourSquareService,
    @ClientID private val clientID: String,
    @ClientSecret private val clientSecret: String
) {

    /**
     * Get details about a venue given a unique venue [id]
     */
    suspend fun getVenueDetails(id: String): VenueDetailsResult {

        return try {
            service.getVenueDetails(
                venueId = id,
                clientId = clientID,
                clientSecret = clientSecret,
            ).response?.venue?.let { venueDetails -> VenueDetailsResult.Success(venueDetails) }
                ?: VenueDetailsResult.Error(WTFException())

        } catch (e: Throwable) {
            VenueDetailsResult.Error(e)
        }
    }

}