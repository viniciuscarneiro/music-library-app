package com.jobsity.musiclibrary.service

import com.jobsity.musiclibrary.dto.SearchResultResponse
import org.springframework.stereotype.Service

@Service
interface SearchService {
    fun search(searchTerm: String): SearchResultResponse
}