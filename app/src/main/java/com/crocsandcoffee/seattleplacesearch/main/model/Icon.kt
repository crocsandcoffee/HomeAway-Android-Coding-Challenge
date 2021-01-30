package com.crocsandcoffee.seattleplacesearch.main.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Omid
 *
 * Pieces of a url that can be put together to retrieve the corresponding icon for a [Category]
 */
@JsonClass(generateAdapter = true)
data class Icon(
    @Json(name = "prefix") val url: String,
    @Json(name = "suffix") val extension: String
)