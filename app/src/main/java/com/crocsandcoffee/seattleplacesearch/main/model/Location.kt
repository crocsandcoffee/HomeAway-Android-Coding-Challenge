package com.crocsandcoffee.seattleplacesearch.main.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Omid
 *
 * Data class holding additional location info about a [Venue]
 *
 * An object containing none, some, or all of address (street address), crossStreet, city,
 * state, postalCode, country, lat, lng, and distance.
 *
 * All fields are strings, except for lat, lng, and distance.
 *
 * [distance] is measured in meters.
 */
@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "address") val address: String = "",
    @Json(name = "distance") val distance: Long? = null,
    @Json(name = "postalCode") val postalCode: String = "",
    @Json(name = "cc") val countryCode: String = "",
    @Json(name = "city") val city: String = "",
    @Json(name = "state") val state: String = "",
    @Json(name = "country") val country: String = "",
    @Json(name = "lat") val lat: Double? = null,
    @Json(name = "lng") val long: Double? = null
)