package com.crocsandcoffee.seattleplacesearch.main.api

import com.crocsandcoffee.seattleplacesearch.main.model.Venue
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Omid
 *
 * Data class holding the actual list of venues found in the [SearchVenuesResponse]
 */
@JsonClass(generateAdapter = true)
data class Response(@Json(name = "venues") val venues: List<Venue> = emptyList())