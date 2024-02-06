package com.jobsity.musiclibrary.controller

import com.jobsity.musiclibrary.dto.SearchResultResponse
import com.jobsity.musiclibrary.service.SearchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController(private val searchService: SearchService) {

    @GetMapping("/search")
    fun search(@RequestParam searchTerm: String): ResponseEntity<SearchResultResponse> {
        return ResponseEntity.ok(searchService.search(searchTerm))
    }
}