package com.crocsandcoffee.seattleplacesearch.main.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Omid
 *
 * An data class containing none, some, or all of twitter, phone, and formattedPhone for a [VenueDetails]
 */
@JsonClass(generateAdapter = true)
data class Contact(
    @Json(name = "twitter") val twitter: String? = null,
    @Json(name = "phone") val phone: String? = null,
    @Json(name = "formattedPhone") val formattedPhone: String? = null
)