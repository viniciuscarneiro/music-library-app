package com.jobsity.musiclibrary.controller.reponse

import com.jobsity.musiclibrary.model.LibraryEntity
import com.jobsity.musiclibrary.model.LibraryItemEntity
import com.jobsity.musiclibrary.thridparty.itunes.WrapperType

data class LibraryResponse(
    val id: String?,
    val name: String,
    val libraryItems: Set<LibraryItemResponse>,
) {
    companion object {
        fun of(libraryEntity: LibraryEntity): LibraryResponse {
            return LibraryResponse(
                id = libraryEntity.id,
                name = libraryEntity.name,
                libraryItems = libraryEntity.libraryItems.map { LibraryItemResponse.of(it) }.toSet()
            )
        }
    }
}

data class LibraryItemResponse(
    val id: String?,
    val wrapperType: WrapperType,
    val collectionId: Int? = null,
    val artistId: Int? = null,
    val artistName: String? = null,
    val collectionName: String? = null,
    val releaseDate: String? = null,
    val primaryGenreName: String? = null,
    val kind: String? = null,
    val trackId: Int? = null,
    val trackName: String? = null,
    val trackTimeMillis: Long? = null,
) {
    companion object {
        fun of(libraryItemEntity: LibraryItemEntity): LibraryItemResponse {
            return LibraryItemResponse(
                id = libraryItemEntity.id,
                wrapperType = libraryItemEntity.wrapperType,
                collectionId = libraryItemEntity.collectionId,
                artistId = libraryItemEntity.artistId,
                artistName = libraryItemEntity.artistName,
                collectionName = libraryItemEntity.collectionName,
                releaseDate = libraryItemEntity.releaseDate,
                primaryGenreName = libraryItemEntity.primaryGenreName,
                kind = libraryItemEntity.kind,
                trackId = libraryItemEntity.trackId,
                trackName = libraryItemEntity.trackName,
                trackTimeMillis = libraryItemEntity.trackTimeMillis,
            )
        }
    }
}