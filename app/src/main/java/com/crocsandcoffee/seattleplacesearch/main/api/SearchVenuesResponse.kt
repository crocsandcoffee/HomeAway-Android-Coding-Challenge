package com.crocsandcoffee.seattleplacesearch.main.api

import com.crocsandcoffee.seattleplacesearch.main.model.Venue
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchVenuesResponse(@Json(name = "response") val response: Response)

@JsonClass(generateAdapter = true)
data class Response(@Json(name = "venues") val venues: List<Venue>)
