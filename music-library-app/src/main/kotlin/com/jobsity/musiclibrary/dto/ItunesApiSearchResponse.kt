package com.jobsity.musiclibrary.dto

import com.jobsity.musiclibrary.thridparty.itunes.WrapperType
import com.jobsity.musiclibrary.thridparty.itunes.dto.ItunesApiSearchResult

interface SearchResult {
    val wrapperType: WrapperType
}

data class SearchResultResponse(
    val resultCount: Int = 0,
    val results: List<SearchResult> = emptyList(),
) {

    companion object {
        fun empty(): SearchResultResponse {
            return SearchResultResponse()
        }
    }
}

data class AlbumSearchResult(
    override val wrapperType: WrapperType,
    val collectionId: Long?,
    val artistId: Long?,
    val artistName: String?,
    val collectionName: String?,
    val releaseDate: String?,
) : SearchResult {

    companion object {
        fun of(it: ItunesApiSearchResult): SearchResult {
            return AlbumSearchResult(
                artistId = it.artistId,
                artistName = it.artistName,
                collectionId = it.collectionId,
                collectionName = it.collectionName,
                releaseDate = it.releaseDate,
                wrapperType = WrapperType.parse(it.wrapperType)
            )
        }
    }
}

data class ArtistSearchResult(
    override val wrapperType: WrapperType,
    val artistId: Long?,
    val artistName: String?,
    val primaryGenreName: String?,
) : SearchResult {

    companion object {
        fun of(it: ItunesApiSearchResult): SearchResult {
            return ArtistSearchResult(
                artistId = it.artistId,
                artistName = it.artistName,
                primaryGenreName = it.primaryGenreName,
                wrapperType = WrapperType.parse(it.wrapperType)
            )
        }
    }
}

data class SongSearchResult(
    override val wrapperType: WrapperType,
    val kind: String?,
    val trackId: Long?,
    val collectionId: Long?,
    val artistId: Long?,
    val trackName: String?,
    val artistName: String?,
    val collectionName: String?,
    val primaryGenreName: String?,
    val releaseDate: String?,
    val trackTimeMillis: Long?,
) : SearchResult {

    companion object {
        fun of(it: ItunesApiSearchResult): SearchResult {
            return SongSearchResult(
                wrapperType = WrapperType.parse(it.wrapperType),
                kind = it.kind,
                artistId = it.artistId,
                artistName = it.artistName,
                primaryGenreName = it.primaryGenreName,
                trackId = it.trackId,
                collectionId = it.collectionId,
                trackName = it.trackName,
                collectionName = it.collectionName,
                releaseDate = it.releaseDate,
                trackTimeMillis = it.trackTimeMillis,
            )
        }
    }
}
