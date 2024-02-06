package com.jobsity.musiclibrary.controller

import com.jobsity.musiclibrary.controller.reponse.LibraryResponse
import com.jobsity.musiclibrary.controller.request.*
import com.jobsity.musiclibrary.service.LibraryService
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses


import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/library")
class LibraryController(val libraryService: LibraryService) {

    @PostMapping
    fun createLibrary(@RequestBody @Valid createLibraryRequest: CreateLibraryRequest): ResponseEntity<LibraryResponse> {
        return ResponseEntity.ok(libraryService.createLibrary(createLibraryRequest))
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(oneOf = [AddAlbumToLibraryRequest::class, AddArtistToLibraryRequest::class, AddSongToLibraryRequest::class])
                )]
            )]
    )
    @PutMapping("/{libraryId}")
    fun addLibraryItem(
        @PathVariable libraryId: String,
        @RequestBody @Valid addItemToLibraryRequest: AddItemToLibraryRequest,
    ): ResponseEntity<Any> {
        libraryService.addLibraryItem(libraryId, addItemToLibraryRequest)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{libraryId}")
    fun removeLibrary(
        @PathVariable libraryId: String,
    ): ResponseEntity<Any> {
        libraryService.removeLibrary(libraryId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{libraryId}/{libraryItemId}")
    fun removeLibraryItem(
        @PathVariable libraryId: String,
        @PathVariable libraryItemId: String,
    ): ResponseEntity<Any> {
        libraryService.removeItemFromLibrary(libraryId, libraryItemId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{libraryId}")
    fun getLibrary(@PathVariable libraryId: String): ResponseEntity<LibraryResponse> {
        return ResponseEntity.ok(libraryService.getLibraryById(libraryId))
    }
}
