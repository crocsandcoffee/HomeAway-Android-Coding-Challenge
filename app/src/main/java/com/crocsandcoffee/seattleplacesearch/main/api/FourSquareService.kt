package com.crocsandcoffee.seattleplacesearch.main.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val SEATTLE_WASHINGTON_CENTER = "47.6062,-122.3321"
private const val DEFAULT_LIMIT = 50
private const val API_VERSION = "20190425"

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
     * @param ll required unless near is provided. Latitude and longitude of the user’s location e.g. [SEATTLE_WASHINGTON_CENTER]
     * @param searchTerm a search term to be applied against venue names
     *
     * @param clientId required for user authentication
     * @param clientSecret required for user authentication
     */
    @GET("venues/search?v=$API_VERSION")
    suspend fun searchVenues(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("query") searchTerm: String,
        @Query("ll") ll: String = SEATTLE_WASHINGTON_CENTER,
        @Query("limit") limit: Int = DEFAULT_LIMIT
    ): SearchVenuesResponse

    /**
     * Get the full details about a venue including location, tips, and categories.
     *
     * @param venueId  ID of the venue to retrieve
     *
     * @param clientId required for user authentication
     * @param clientSecret required for user authentication
     */
    @GET("venues/{venueId}?v=$API_VERSION")
    suspend fun getVenueDetails(
        @Path("venueId") venueId: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String
    ) : GetVenueDetailsResponse
}