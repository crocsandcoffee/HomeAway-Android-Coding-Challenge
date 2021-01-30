package com.crocsandcoffee.seattleplacesearch.main.model

import android.net.Uri
import androidx.core.net.toUri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// supported sizes are 32, 44, 64, and 88
private const val ICON_SIZE = 64

/**
 * @author Omid
 *
 * Data class holding information applied to a [Venue]
 *
 * @param id unique identifier for this category.
 * @param name of the category.
 * @param primary if this is the primary category for parent venue object.
 * @param icon pieces needed to construct category icons at various sizes.
 */
@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "id") val id: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "primary") val primary: Boolean = false,
    @Json(name = "icon") val icon: Icon? = null
) {
    val iconUri: Uri = icon?.let { icon -> "${icon.url}$ICON_SIZE${icon.extension}".toUri() } ?: Uri.EMPTY
}