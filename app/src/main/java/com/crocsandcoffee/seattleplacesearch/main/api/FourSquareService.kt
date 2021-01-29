package com.crocsandcoffee.seattleplacesearch.main.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val SEATTLE_WASHINGTON = "47.6062,-122.3321"
private const val DEFAULT_LIMIT = 20

interface FourSquareService {

    @GET("venues/search?v=20190425&limit=20")
    suspend fun searchVenues(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("query") searchTerm: String,
        @Query("l") ll: String = SEATTLE_WASHINGTON,
        @Query("limit") limit: Int = DEFAULT_LIMIT
    ) : SearchVenuesResponse


}