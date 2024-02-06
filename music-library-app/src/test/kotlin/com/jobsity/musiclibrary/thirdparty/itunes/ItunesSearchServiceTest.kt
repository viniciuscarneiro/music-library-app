package com.jobsity.musiclibrary.thirdparty.itunes

import com.jobsity.musiclibrary.error.InvalidWrapperTypeException
import com.jobsity.musiclibrary.thridparty.itunes.ItunesApi
import com.jobsity.musiclibrary.thridparty.itunes.ItunesSearchService
import com.jobsity.musiclibrary.thridparty.itunes.dto.ItunesApiSearchResponse
import com.jobsity.musiclibrary.thridparty.itunes.dto.ItunesApiSearchResult
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.`when` as mockWhen

@ExtendWith(MockitoExtension::class)
class ItunesSearchServiceTest {

    @Mock
    private lateinit var itunesApi: ItunesApi

    @InjectMocks
    private lateinit var itunesSearchService: ItunesSearchService

    @Test
    fun `should search on Itunes with valid results`() {
        val searchTerm = "test"
        val itunesApiSearchResponse = ItunesApiSearchResponse(
            resultCount = 2,
            results = listOf(
                ItunesApiSearchResult(wrapperType = "artist"),
                ItunesApiSearchResult(wrapperType = "collection")
            )
        )
        mockWhen(itunesApi.search(searchTerm)).thenReturn(itunesApiSearchResponse)
        val result = itunesSearchService.search(searchTerm)
        assert(result.resultCount == 2)
    }

    @Test
    fun `should return empty results when Itunes result is empty`() {
        val searchTerm = "test"
        val itunesApiSearchResponse = ItunesApiSearchResponse(resultCount = 0, results = emptyList())
        mockWhen(itunesApi.search(searchTerm)).thenReturn(itunesApiSearchResponse)
        val result = itunesSearchService.search(searchTerm)
        assert(result.resultCount == 0)
    }

    @Test
    fun `should throw InvalidWrapperTypeException when wrapper type is invalid`() {
        val searchTerm = "test"
        val itunesApiSearchResponse = ItunesApiSearchResponse(
            resultCount = 1,
            results = listOf(
                ItunesApiSearchResult(wrapperType = "invalid_type")
            )
        )
        mockWhen(itunesApi.search(searchTerm)).thenReturn(itunesApiSearchResponse)
        assertThrows<InvalidWrapperTypeException> {
            itunesSearchService.search(searchTerm)
        }
    }
}
