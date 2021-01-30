package com.crocsandcoffee.seattleplacesearch.main.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class for deserializing likes metadata from [VenueDetails]
 */
@JsonClass(generateAdapter = true)
data class Likes(
    @Json(name = "count") val count: Int = 0,
    @Json(name = "summary") val summary: String = ""
)