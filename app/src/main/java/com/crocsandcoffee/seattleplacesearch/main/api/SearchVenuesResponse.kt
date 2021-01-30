package com.crocsandcoffee.seattleplacesearch.main.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Omid
 *
 * Data class to hold search result responses from [FourSquareService.searchVenues] API calls
 */
@JsonClass(generateAdapter = true)
data class SearchVenuesResponse(@Json(name = "response") val response: Response? = null)