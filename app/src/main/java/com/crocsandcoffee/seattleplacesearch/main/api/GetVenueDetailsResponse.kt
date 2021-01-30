package com.crocsandcoffee.seattleplacesearch.main.api

import com.crocsandcoffee.seattleplacesearch.main.model.VenueDetails
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Omid
 *
 * Data class to deserialize json response from [FourSquareService.getVenueDetails]
 */
@JsonClass(generateAdapter = true)
data class GetVenueDetailsResponse(
    @Json(name = "response") val response: VenueDetailsResponse? = null
)

/**
 * Data class to deserialize the venue details from [GetVenueDetailsResponse]
 */
@JsonClass(generateAdapter = true)
data class VenueDetailsResponse(
    @Json(name = "venue") val venue: VenueDetails
)