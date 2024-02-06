package com.jobsity.musiclibrary.thirdparty.itunes

import com.fasterxml.jackson.databind.ObjectMapper
import com.jobsity.musiclibrary.thridparty.itunes.ItunesApi
import com.jobsity.musiclibrary.thridparty.itunes.dto.ItunesApiSearchResponse
import com.jobsity.musiclibrary.thridparty.itunes.dto.ItunesApiSearchResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.client.RestTemplate
import java.net.URI
import org.mockito.Mockito.`when` as mockWhen

@ExtendWith(MockitoExtension::class)
class ItunesApiTest {

    companion object {
        val API_JSON_RESPONSE = """
            {
             "resultCount":1,
             "results": [{"wrapperType":"artist", "artistType":"Artist", "artistName":"Post Malone", "artistLinkUrl":"https://music.apple.com/us/artist/post-malone/966309175?uo=4", "artistId":966309175, "amgArtistId":3128337, "primaryGenreName":"Hip-Hop/Rap", "primaryGenreId":18},]
            }
        """.trimIndent()
    }

    @Mock
    private lateinit var restTemplate: RestTemplate

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @InjectMocks
    private lateinit var itunesApi: ItunesApi

    @Test
    fun `should return expected result when searching for Post Malone`() {

        val searchTerm = "post malone 2022"
        val uri =
            URI.create("https://itunes.apple.com/search?term=post+malone+2022&entity=album,musicArtist,song")

        mockWhen(restTemplate.getForObject(uri, String::class.java)).thenReturn(API_JSON_RESPONSE)
        val expectedResult = ItunesApiSearchResponse(
            resultCount = 1, results = listOf(
                ItunesApiSearchResult(
                    wrapperType = "artist",
                    artistType = "Artist",
                    artistName = "Post Malone",
                    artistLinkUrl = "https://music.apple.com/us/artist/post-malone/966309175?uo=4",
                    artistId = 966309175,
                    amgArtistId = 3128337,
                    primaryGenreName = "Hip-Hop/Rap",
                    primaryGenreId = 18,
                )
            )
        )
        mockWhen(objectMapper.readValue(API_JSON_RESPONSE, ItunesApiSearchResponse::class.java)).thenReturn(
            expectedResult
        )

        val result = itunesApi.search(searchTerm)

        assertNotNull(result)
        assertEquals(expectedResult, result)
        assertEquals("Post Malone", result?.results?.get(0)?.artistName)
        verify(restTemplate, times(1)).getForObject(uri, String::class.java)
    }
}
