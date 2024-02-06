package com.jobsity.musiclibrary.thridparty.itunes

import com.fasterxml.jackson.databind.ObjectMapper
import com.jobsity.musiclibrary.thridparty.itunes.dto.ItunesApiSearchResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class ItunesApi(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
) {
    companion object {
        const val ITUNES_SEARCH_BASE_URL = "https://itunes.apple.com/search"
        const val TERM = "term"
        const val ENTITY = "entity"
    }

    fun search(searchTerm: String): ItunesApiSearchResponse? {
        val encodedSearchTerm = encodeSearchTerm(searchTerm)
        val queryParams = mapOf(
            TERM to encodedSearchTerm,
            ENTITY to buildEntities(),
        )

        val uri = buildUri(queryParams)
        val result = restTemplate.getForObject(uri, String::class.java)

        return objectMapper.readValue(result, ItunesApiSearchResponse::class.java)
    }

    private fun buildEntities() = ItunesEntity.entries.joinToString(",") { it.itunesName }

    private fun encodeSearchTerm(searchTerm: String): String {
        return searchTerm.replace(Regex("[^a-zA-Z0-9.*_+-]")) {
            when (it.value[0]) {
                ' ' -> "+"
                else -> "%${it.value[0].code.toString(16).uppercase(Locale.getDefault())}"
            }
        }
    }

    private fun buildUri(queryParams: Map<String, String>): java.net.URI {
        return UriComponentsBuilder.fromHttpUrl(ITUNES_SEARCH_BASE_URL)
            .apply {
                queryParams.forEach { (key, value) -> queryParam(key, value) }
            }
            .build()
            .toUri()
    }
}
