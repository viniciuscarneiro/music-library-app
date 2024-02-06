package com.jobsity.musiclibrary.service

import com.jobsity.musiclibrary.controller.request.AddAlbumToLibraryRequest
import com.jobsity.musiclibrary.controller.request.CreateLibraryRequest
import com.jobsity.musiclibrary.error.DuplicateItemException
import com.jobsity.musiclibrary.error.LibraryItemNotFoundException
import com.jobsity.musiclibrary.error.LibraryNotFoundException
import com.jobsity.musiclibrary.model.LibraryEntity
import com.jobsity.musiclibrary.model.LibraryItemEntity
import com.jobsity.musiclibrary.repository.LibraryItemRepository
import com.jobsity.musiclibrary.repository.LibraryRepository
import com.jobsity.musiclibrary.thridparty.itunes.WrapperType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.`when` as mockitoWhen

@ExtendWith(MockitoExtension::class)
class LibraryServiceTest {
    companion object {
        private const val ALBUM_NAME = "Test Album"
        private const val ARTIST_ID = 1
        private const val ARTIST_NAME = "Test Artist"
        private const val COLLECTION_ID = 1
        private const val LIBRARY_ENTITY_ID = "1"
        private const val LIBRARY_ENTITY_NAME = "Library test"
        private const val LIBRARY_ITEM_ENTITY_ID = "item_id"
        private const val RELEASE_DATE = "2022-01-01"
    }

    @Mock
    private lateinit var libraryRepository: LibraryRepository

    @Mock
    private lateinit var libraryItemRepository: LibraryItemRepository

    @InjectMocks
    private lateinit var libraryService: LibraryService

    @Test
    fun `should create new library`() {
        val createdLibrary = LibraryEntity(id = LIBRARY_ENTITY_ID, name = LIBRARY_ENTITY_NAME)
        given(libraryRepository.save(any())).willReturn(createdLibrary)
        val createLibraryRequest = CreateLibraryRequest(name = LIBRARY_ENTITY_NAME)
        val result = libraryService.createLibrary(createLibraryRequest)
        assertEquals(createdLibrary.id, result.id)
        assertEquals(createdLibrary.name, result.name)
    }

    @Test
    fun `should add new item to the library`() {
        val existingLibrary = LibraryEntity(id = LIBRARY_ENTITY_ID, name = LIBRARY_ENTITY_NAME)
        val addItemRequest = AddAlbumToLibraryRequest(
            wrapperType = WrapperType.COLLECTION,
            collectionId = COLLECTION_ID,
            artistId = ARTIST_ID,
            artistName = ARTIST_NAME,
            collectionName = ALBUM_NAME,
            releaseDate = RELEASE_DATE
        )
        given(libraryRepository.findLibraryEntityById(LIBRARY_ENTITY_ID)).willReturn(existingLibrary)
        given(
            libraryItemRepository.findByWrapperTypeAndCollectionIdAndArtistIdAndKindAndTrackId(
                wrapperType = addItemRequest.wrapperType,
                collectionId = addItemRequest.collectionId,
                artistId = addItemRequest.artistId,
                kind = null,
                trackId = null
            )
        ).willReturn(null)
        val existingLibraryItem = mock<LibraryItemEntity>()

        mockitoWhen(libraryItemRepository.save(any())).thenReturn(existingLibraryItem)

        libraryService.addLibraryItem(LIBRARY_ENTITY_ID, addItemRequest)

        verify(libraryItemRepository).findByWrapperTypeAndCollectionIdAndArtistIdAndKindAndTrackId(
            wrapperType = addItemRequest.wrapperType,
            collectionId = addItemRequest.collectionId,
            artistId = addItemRequest.artistId,
            kind = null,
            trackId = null
        )
        verify(libraryRepository).findLibraryEntityById(LIBRARY_ENTITY_ID)
        verify(libraryRepository).save(existingLibrary)
    }


    @Test
    fun `should not add item to the library when its already exists`() {
        val existingLibrary = mock<LibraryEntity>()
        val addItemRequest = AddAlbumToLibraryRequest(
            wrapperType = WrapperType.COLLECTION,
            collectionId = COLLECTION_ID,
            artistId = ARTIST_ID,
            artistName = ARTIST_NAME,
            collectionName = ALBUM_NAME,
            releaseDate = RELEASE_DATE
        )
        val libraryItems = mock<MutableSet<LibraryItemEntity>>()
        given(libraryItems.contains(any())).willReturn(true)
        given(existingLibrary.libraryItems).willReturn(libraryItems)
        given(libraryRepository.findLibraryEntityById(LIBRARY_ENTITY_ID)).willReturn(existingLibrary)
        assertThrows(DuplicateItemException::class.java) {
            libraryService.addLibraryItem(LIBRARY_ENTITY_ID, addItemRequest)
        }
        verify(libraryRepository, only()).findLibraryEntityById(LIBRARY_ENTITY_ID)
        verify(libraryRepository, never()).save(existingLibrary)
        verify(libraryItemRepository, never()).findByWrapperTypeAndCollectionIdAndArtistIdAndKindAndTrackId(
            wrapperType = addItemRequest.wrapperType,
            collectionId = addItemRequest.collectionId,
            artistId = addItemRequest.artistId,
            kind = null,
            trackId = null
        )
    }

    @Test
    fun `should not add item to the library whe library was not found`() {
        val addItemRequest = mock<AddAlbumToLibraryRequest>()
        given(libraryRepository.findLibraryEntityById(LIBRARY_ENTITY_ID)).willReturn(null)

        assertThrows(LibraryNotFoundException::class.java) {
            libraryService.addLibraryItem(LIBRARY_ENTITY_ID, addItemRequest)
        }
        verify(libraryItemRepository, never()).save(any())
        verify(libraryRepository, only()).findLibraryEntityById(LIBRARY_ENTITY_ID)
        verify(libraryItemRepository, never()).findByWrapperTypeAndCollectionIdAndArtistIdAndKindAndTrackId(
            wrapperType = addItemRequest.wrapperType,
            collectionId = addItemRequest.collectionId,
            artistId = addItemRequest.artistId,
            kind = null,
            trackId = null
        )
    }

    @Test
    fun `should return library by given id`() {
        val existingLibrary = LibraryEntity(id = LIBRARY_ENTITY_ID, name = LIBRARY_ENTITY_NAME)
        given(libraryRepository.findLibraryEntityById(LIBRARY_ENTITY_ID)).willReturn(existingLibrary)
        val result = libraryService.getLibraryById(LIBRARY_ENTITY_ID)
        assertEquals(existingLibrary.id, result.id)
        assertEquals(existingLibrary.name, result.name)
        verify(libraryRepository, only()).findLibraryEntityById(LIBRARY_ENTITY_ID)
    }

    @Test
    fun `should remove library by given id`() {
        libraryService.removeLibrary(LIBRARY_ENTITY_ID)
        verify(libraryRepository).deleteById(LIBRARY_ENTITY_ID)
    }

    @Test
    fun `should remove library item by given libraryId and libraryItemId`() {
        val existingLibrary = mock<LibraryEntity>()
        val libraryItems = mock<MutableSet<LibraryItemEntity>>()
        given(libraryItems.removeIf(any())).willReturn(true)
        given(existingLibrary.libraryItems).willReturn(libraryItems)
        given(libraryRepository.findLibraryEntityById(LIBRARY_ENTITY_ID)).willReturn(existingLibrary)
        assertDoesNotThrow { libraryService.removeItemFromLibrary(LIBRARY_ENTITY_ID, LIBRARY_ITEM_ENTITY_ID) }
        verify(libraryRepository).findLibraryEntityById(LIBRARY_ENTITY_ID)
        verify(libraryRepository).save(existingLibrary)
    }

    @Test
    fun `should not remove library item when given libraryItemId not belongs to the library`() {
        val existingLibrary = mock<LibraryEntity>()
        val libraryItems = mock<MutableSet<LibraryItemEntity>>()
        given(libraryItems.removeIf(any())).willReturn(false)
        given(existingLibrary.libraryItems).willReturn(libraryItems)
        given(libraryRepository.findLibraryEntityById(LIBRARY_ENTITY_ID)).willReturn(existingLibrary)
        assertThrows(LibraryItemNotFoundException::class.java) {
            libraryService.removeItemFromLibrary(
                LIBRARY_ENTITY_ID,
                LIBRARY_ITEM_ENTITY_ID
            )
        }
        verify(libraryRepository).findLibraryEntityById(LIBRARY_ENTITY_ID)
        verify(libraryRepository, never()).save(existingLibrary)
    }
}
