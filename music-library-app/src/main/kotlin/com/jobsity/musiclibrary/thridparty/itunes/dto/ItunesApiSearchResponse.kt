package com.jobsity.musiclibrary.thridparty.itunes.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ItunesApiSearchResponse(
    val resultCount: Int,
    val results: List<ItunesApiSearchResult>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ItunesApiSearchResult(
    val artistId: Long? = null,
    val artistName: String? = null,
    val artistViewUrl: String? = null,
    val artistType: String? = null,
    val artistLinkUrl: String? = null,
    val amgArtistId: Long? = null,
    val primaryGenreId: Long? = null,
    val artworkUrl100: String? = null,
    val artworkUrl30: String? = null,
    val artworkUrl60: String? = null,
    val collectionArtistName: String? = null,
    val collectionCensoredName: String? = null,
    val collectionExplicitness: String? = null,
    val collectionId: Long? = null,
    val collectionName: String? = null,
    val collectionPrice: Double? = null,
    val collectionViewUrl: String? = null,
    val contentAdvisoryRating: String? = null,
    val country: String? = null,
    val currency: String? = null,
    val discCount: Int? = null,
    val discNumber: Int? = null,
    val isStreamable: Boolean? = null,
    val kind: String? = null,
    val previewUrl: String? = null,
    val primaryGenreName: String? = null,
    //"releaseDate":"2019-12-05T08:00:00Z"
    val releaseDate: String? = null,
    val trackCensoredName: String? = null,
    val trackCount: Int? = null,
    val trackExplicitness: String? = null,
    val trackId: Long? = null,
    val trackName: String? = null,
    val trackNumber: Int? = null,
    val trackPrice: Double? = null,
    val trackTimeMillis: Long? = null,
    val trackViewUrl: String? = null,
    val wrapperType: String? = null,
) {

}