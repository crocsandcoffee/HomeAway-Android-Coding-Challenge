package com.crocsandcoffee.seattleplacesearch.main.model

/**
 * @author Omid
 *
 * Result wrapper used when fetching details about a Venue
 */
sealed class VenueDetailsResult {

    /** Signals an error when fetching details about a venue */
    data class Error(val exception: Throwable) : VenueDetailsResult()

    /** Successful response containing details about a Venue */
    data class Success(val details: VenueDetails) : VenueDetailsResult()
}