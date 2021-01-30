package com.crocsandcoffee.seattleplacesearch.main.model

import android.net.Uri
import androidx.core.net.toUri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author Omid
 *
 * Photo determined to be the best photo for the venue based on user up votes
 *
 *
 * @param id unique string identifier for this photo
 * @param baseUrl start of the URL for this photo
 * @param fileExt end of the URL for this photo
 * @param width of this photo in pixels
 * @param height of this photo in pixels
 * @param visibility  Can be one of:
 *    public (everybody can see, including on the venue page),
 *    friends (only the poster's friends can see),
 *    or private (only the poster can see)
 */
@JsonClass(generateAdapter = true)
data class Photo(
    @Json(name = "id") val id: String,
    @Json(name = "prefix") val baseUrl: String,
    @Json(name = "suffix") val fileExt: String,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int,
    @Json(name = "visibility") val visibility: String
) {

    val photoUrI: Uri = "${baseUrl}${width}x${height}$fileExt".toUri()
}