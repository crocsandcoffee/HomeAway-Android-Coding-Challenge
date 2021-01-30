package com.crocsandcoffee.seattleplacesearch.main.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Omid
 *
 * Data class holding additional location info about a [Venue]
 */
@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "address") val address: String = "",
    @Json(name = "distance") val distance: Long? = null,
    @Json(name = "postalCode") val postalCode: String = "",
    @Json(name = "cc") val countryCode: String = "",
    @Json(name = "city") val city: String = "",
    @Json(name = "state") val state: String = "",
    @Json(name = "country") val country: String = ""
)