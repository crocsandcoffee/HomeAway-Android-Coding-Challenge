package com.crocsandcoffee.seattleplacesearch.main.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val SEATTLE_WASHINGTON = "47.6062,-122.3321"
private const val DEFAULT_LIMIT = 50

/**
 * @author Omid
 *
 * FourSquare API client setup through Retrofit to reach the
 * Places API (https://developer.foursquare.com/docs/places-api/)
 */
interface FourSquareService {

    /**
     * Venue Search
     *
     * Returns a list of venues near the current location, optionally matching a search term.
     *
     * @param limit number of results to return, up to 50.
     * @param ll required unless near is provided. Latitude and longitude of the user’s location e.g. [SEATTLE_WASHINGTON]
     * @param searchTerm a search term to be applied against venue names
     *
     * @param clientId required for user authentication
     * @param clientSecret required for user authentication
     */
    @GET("venues/search?v=20190425")
    suspend fun searchVenues(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("query") searchTerm: String,
        @Query("ll") ll: String = SEATTLE_WASHINGTON,
        @Query("limit") limit: Int = DEFAULT_LIMIT
    ) : SearchVenuesResponse
}