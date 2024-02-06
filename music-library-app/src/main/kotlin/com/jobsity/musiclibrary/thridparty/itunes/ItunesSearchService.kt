package com.jobsity.musiclibrary.thridparty.itunes

import com.jobsity.musiclibrary.error.InvalidWrapperTypeException
import com.jobsity.musiclibrary.dto.*
import com.jobsity.musiclibrary.thridparty.itunes.dto.ItunesApiSearchResult
import com.jobsity.musiclibrary.service.SearchService
import org.springframework.stereotype.Service

@Service
class ItunesSearchService(private val itunesApi: ItunesApi) : SearchService {

    override fun search(searchTerm: String): SearchResultResponse {
        return searchOnItunes(searchTerm)
    }

    private fun searchOnItunes(searchTerm: String): SearchResultResponse {
        return itunesApi.search(searchTerm)
            ?.takeIf { it.resultCount > 0 }
            ?.let {
                SearchResultResponse(
                    resultCount = it.resultCount,
                    results = toSearchResultResponse(it.results)
                )
            }
            ?: SearchResultResponse.empty()
    }

    private fun toSearchResultResponse(itunesApiSearchResponse: List<ItunesApiSearchResult>): List<SearchResult> {
        return itunesApiSearchResponse.map {
            when (it.wrapperType) {
                WrapperType.ARTIST.itunesName -> {
                    ArtistSearchResult.of(it)
                }

                WrapperType.COLLECTION.itunesName -> {
                    AlbumSearchResult.of(it)
                }

                WrapperType.TRACK.itunesName -> {
                    SongSearchResult.of(it)
                }

                else -> {
                    throw InvalidWrapperTypeException("Unable to parse request with wrapper type[${it.wrapperType}]")
                }
            }
        }.toList()
    }
}