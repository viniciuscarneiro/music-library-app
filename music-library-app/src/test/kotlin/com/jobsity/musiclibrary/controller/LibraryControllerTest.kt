package com.jobsity.musiclibrary.controller

import com.jobsity.musiclibrary.TestUtils
import com.jobsity.musiclibrary.controller.reponse.LibraryResponse
import com.jobsity.musiclibrary.controller.request.AddAlbumToLibraryRequest
import com.jobsity.musiclibrary.controller.request.CreateLibraryRequest
import com.jobsity.musiclibrary.error.LibraryNotFoundException
import com.jobsity.musiclibrary.service.LibraryService
import com.jobsity.musiclibrary.thridparty.itunes.WrapperType
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(LibraryController::class)
class LibraryControllerTest {
    @MockBean
    private lateinit var libraryService: LibraryService

    @Autowired
    private lateinit var mockMvc: MockMvc

    companion object {
        private const val LIBRARY_NAME = "Test Library"
        private const val LIBRARY_PATH = "/library"
        private const val LIBRARY_ID = "1"
        private const val ITEM_ID = "item_id"
        private const val INVALID_LIBRARY_ID = "invalid_id"
        private const val INVALID_NAME = ""
    }

    @Test
    fun `createLibrary should return 200 and created library`() {
        val createLibraryRequest = CreateLibraryRequest(name = LIBRARY_NAME)
        val createdLibrary = LibraryResponse(id = LIBRARY_ID, name = LIBRARY_NAME, libraryItems = mutableSetOf())
        given(libraryService.createLibrary(createLibraryRequest)).willReturn(createdLibrary)
        mockMvc.perform(
            post(LIBRARY_PATH)
                .content(TestUtils.convertObjectToJson(createLibraryRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(TestUtils.convertObjectToJson(createdLibrary)))
    }

    @Test
    fun `addLibraryItem should return 204 on success`() {
        val addItemToLibraryRequest = AddAlbumToLibraryRequest(
            wrapperType = WrapperType.COLLECTION,
            collectionId = 1,
            artistId = 1,
            artistName = "Test Artist",
            collectionName = "Test Album",
            releaseDate = "2022-01-01"
        )
        mockMvc.perform(
            put("$LIBRARY_PATH/$LIBRARY_ID")
                .content(TestUtils.convertObjectToJson(addItemToLibraryRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)
    }

    @Test
    fun `removeLibrary should return 204 on success`() {
        mockMvc.perform(delete("$LIBRARY_PATH/$LIBRARY_ID"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `removeLibraryItem should return 204 on success`() {
        mockMvc.perform(delete("$LIBRARY_PATH/$LIBRARY_ID/$ITEM_ID"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `getLibrary should return 200 and the library`() {
        val libraryResponse = LibraryResponse(id = LIBRARY_ID, name = LIBRARY_NAME, libraryItems = mutableSetOf())
        given(libraryService.getLibraryById(LIBRARY_ID)).willReturn(libraryResponse)
        mockMvc.perform(get("$LIBRARY_PATH/$LIBRARY_ID"))
            .andExpect(status().isOk)
            .andExpect(content().json(TestUtils.convertObjectToJson(libraryResponse)))
    }

    @Test
    fun `createLibrary should return 400 on invalid request`() {
        val invalidCreateLibraryRequest = CreateLibraryRequest(name = INVALID_NAME)
        mockMvc.perform(
            post(LIBRARY_PATH)
                .content(TestUtils.convertObjectToJson(invalidCreateLibraryRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }


    @Test
    fun `addLibraryItem should return 404 if library does not exist`() {
        val addItemToLibraryRequest = AddAlbumToLibraryRequest(
            wrapperType = WrapperType.COLLECTION,
            collectionId = 1,
            artistId = 1,
            artistName = "Test Artist",
            collectionName = "Test Album",
            releaseDate = "2022-01-01"
        )
        given(libraryService.addLibraryItem(INVALID_LIBRARY_ID, addItemToLibraryRequest))
            .willThrow(
                LibraryNotFoundException(
                    "Library does not exist",
                    mapOf("id" to INVALID_LIBRARY_ID)
                )
            )

        mockMvc.perform(
            put("$LIBRARY_PATH/$INVALID_LIBRARY_ID")
                .content(TestUtils.convertObjectToJson(addItemToLibraryRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
    }
}
