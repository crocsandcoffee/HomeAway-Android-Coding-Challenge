package com.crocsandcoffee.seattleplacesearch.main.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Omid
 *
 * Data class representing a single Venue
 */
@JsonClass(generateAdapter = true)
data class Venue(
    @Json(name = "id") val id: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "location") val location: Location? = null,
    @Json(name = "categories") val categories: List<Category> = emptyList()
)