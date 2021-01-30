package com.crocsandcoffee.seattleplacesearch.main.model

/**
 * @author Omid
 *
 * Result wrapper used when fetching a list of [Venue]s
 *
 */
sealed class VenueSearchResult {

    /** Signals an error when fetching venues*/
    data class Error(val exception: Throwable) : VenueSearchResult()

    /** Successful response containing list of venues */
    data class Success(val venues: List<Venue>) : VenueSearchResult()
}