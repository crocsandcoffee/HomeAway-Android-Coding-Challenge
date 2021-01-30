package com.crocsandcoffee.seattleplacesearch.main.model

sealed class VenueSearchResult {
    data class Error(val exception: Throwable) : VenueSearchResult()
    data class Success(val venues: List<Venue>) : VenueSearchResult()
}