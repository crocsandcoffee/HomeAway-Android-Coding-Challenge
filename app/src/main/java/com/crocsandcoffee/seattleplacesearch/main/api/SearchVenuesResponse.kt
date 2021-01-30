package com.crocsandcoffee.seattleplacesearch.main.api

import com.crocsandcoffee.seattleplacesearch.main.model.Venue
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Omid
 *
 * Data class to hold search result responses from [FourSquareService.searchVenues] API calls
 */
@JsonClass(generateAdapter = true)
data class SearchVenuesResponse(@Json(name = "response") val response: VenuesResponse? = null)

/**
 * Data class holding the actual list of venues found in the [SearchVenuesResponse]
 */
@JsonClass(generateAdapter = true)
data class VenuesResponse(@Json(name = "venues") val venues: List<Venue> = emptyList())