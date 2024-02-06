package com.jobsity.musiclibrary.controller

import com.jobsity.musiclibrary.dto.SearchResultResponse
import com.jobsity.musiclibrary.service.SearchService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.mockito.Mockito.`when` as mockWhen

@WebMvcTest(SearchController::class)
class SearchControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var searchService: SearchService

    @Test
    fun `should search successfully `() {
        val searchTerm = "post malone"
        val expectedResult = SearchResultResponse(resultCount = 1, results = emptyList())
        mockWhen(searchService.search(searchTerm)).thenReturn(expectedResult)
        mockMvc.perform(
            get("/search?searchTerm=$searchTerm")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }
}
