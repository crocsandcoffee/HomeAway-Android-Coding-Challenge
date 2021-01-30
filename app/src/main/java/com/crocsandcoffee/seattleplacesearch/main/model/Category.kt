package com.crocsandcoffee.seattleplacesearch.main.model

import android.net.Uri
import androidx.core.net.toUri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

private const val ICON_SIZE = 64

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "id") val id: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "primary") val primary: Boolean = false,
    @Json(name = "icon") val icon: Icon? = null
) {
    val iconUri: Uri = icon?.let { icon -> "${icon.url}$ICON_SIZE${icon.extension}".toUri() } ?: Uri.EMPTY
}