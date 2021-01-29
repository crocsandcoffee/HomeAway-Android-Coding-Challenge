package com.crocsandcoffee.seattleplacesearch.main.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "id") val id: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "primary") val primary: Boolean = false,
    @Json(name = "icon") val icon: Icon? = null
)