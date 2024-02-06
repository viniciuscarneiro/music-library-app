package com.jobsity.musiclibrary.service

import com.jobsity.musiclibrary.controller.reponse.LibraryResponse
import com.jobsity.musiclibrary.controller.request.AddItemToLibraryRequest
import com.jobsity.musiclibrary.controller.request.CreateLibraryRequest
import com.jobsity.musiclibrary.error.DuplicateItemException
import com.jobsity.musiclibrary.error.LibraryItemNotFoundException
import com.jobsity.musiclibrary.error.LibraryNotFoundException
import com.jobsity.musiclibrary.model.LibraryEntity
import com.jobsity.musiclibrary.model.LibraryItemEntity
import com.jobsity.musiclibrary.repository.LibraryItemRepository
import com.jobsity.musiclibrary.repository.LibraryRepository
import org.springframework.stereotype.Service

@Service
class LibraryService(
    private val libraryRepository: LibraryRepository,
    private val libraryItemRepository: LibraryItemRepository,
) {

    fun createLibrary(createLibraryRequest: CreateLibraryRequest): LibraryResponse {
        val createdLibraryEntity = libraryRepository.save(LibraryEntity.from(createLibraryRequest))
        return LibraryResponse.of(createdLibraryEntity)
    }

    fun addLibraryItem(id: String, addItemToLibraryRequest: AddItemToLibraryRequest) {
        val existingLibrary = retrieveLibraryEntityById(id)
        val itemToAdd = LibraryItemEntity.of(addItemToLibraryRequest)

        validateItemNotExistsInLibrary(existingLibrary, itemToAdd)

        val existingItem = libraryItemRepository.findByWrapperTypeAndCollectionIdAndArtistIdAndKindAndTrackId(
            itemToAdd.wrapperType, itemToAdd.collectionId, itemToAdd.artistId, itemToAdd.kind, itemToAdd.trackId
        )

        if (existingItem != null) {
            existingLibrary.libraryItems.add(existingItem)
        } else {
            createNewItem(itemToAdd, existingLibrary)
        }

        libraryRepository.save(existingLibrary)
    }

    fun getLibraryById(id: String): LibraryResponse {
        return retrieveLibraryEntityById(id).let {
            LibraryResponse.of(it)
        }
    }

    fun removeLibrary(id: String) {
        libraryRepository.deleteById(id)
    }

    fun removeItemFromLibrary(libraryId: String, libraryItemId: String) {
        val foundLibrary = retrieveLibraryEntityById(libraryId)
        val removed = foundLibrary.libraryItems.removeIf { it.id == libraryItemId }
        if (!removed) {
            throw LibraryItemNotFoundException(
                "Library item does not exist",
                mapOf("id" to libraryItemId)
            )
        }
        libraryRepository.save(foundLibrary)
    }

    private fun createNewItem(
        itemToAdd: LibraryItemEntity,
        existingLibrary: LibraryEntity,
    ) {
        val addedItem = libraryItemRepository.save(itemToAdd)
        existingLibrary.libraryItems.add(addedItem)
    }

    private fun validateItemNotExistsInLibrary(library: LibraryEntity, itemToAdd: LibraryItemEntity) {
        if (library.libraryItems.contains(itemToAdd)) {
            throw DuplicateItemException("Item already exists in the library.")
        }
    }

    private fun retrieveLibraryEntityById(id: String): LibraryEntity {
        return libraryRepository.findLibraryEntityById(id) ?: throw LibraryNotFoundException(
            "Library does not exist",
            mapOf("id" to id)
        )
    }
}
