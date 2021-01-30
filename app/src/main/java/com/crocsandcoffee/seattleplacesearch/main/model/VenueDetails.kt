package com.crocsandcoffee.seattleplacesearch.main.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Omid
 *
 *
 * @param id unique string identifier for this venue
 * @param name the best known name for this venue
 * @param contact See [Contact]
 * @param location See [Location]
 * @param categories See [Category]
 * @param verified Boolean indicating whether the owner of this business has claimed it and verified the information.
 * @param likes see [Likes]
 * @param bestPhoto see [Photo]
 */
@JsonClass(generateAdapter = true)
data class VenueDetails(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "contact") val contact: Contact? = null,
    @Json(name = "location") val location: Location? = null,
    @Json(name = "categories") val categories: List<Category> = emptyList(),
    @Json(name = "verified") val verified: Boolean = false,
    @Json(name = "url") val url: String? = null,
    @Json(name = "canonicalUrl") val canonicalUrl: String? = null,
    @Json(name = "likes") val likes: Likes? = null,
    @Json(name = "bestPhoto") val bestPhoto: Photo? = null
)