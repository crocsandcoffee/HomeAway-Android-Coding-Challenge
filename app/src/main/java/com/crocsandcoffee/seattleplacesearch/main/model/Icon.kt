package com.crocsandcoffee.seattleplacesearch.main.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Icon(
    @Json(name = "prefix") val url: String,
    @Json(name = "suffix") val extension: String
)