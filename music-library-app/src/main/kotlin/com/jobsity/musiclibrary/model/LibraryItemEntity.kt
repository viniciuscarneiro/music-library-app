package com.jobsity.musiclibrary.model

import com.jobsity.musiclibrary.controller.request.AddAlbumToLibraryRequest
import com.jobsity.musiclibrary.controller.request.AddArtistToLibraryRequest
import com.jobsity.musiclibrary.controller.request.AddItemToLibraryRequest
import com.jobsity.musiclibrary.controller.request.AddSongToLibraryRequest
import com.jobsity.musiclibrary.thridparty.itunes.WrapperType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "library_items")
class LibraryItemEntity(
    @Id
    @MongoId
    val id: String? = null,
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
        fun of(request: AddItemToLibraryRequest): LibraryItemEntity {
            when (request) {
                is AddAlbumToLibraryRequest -> {
                    return LibraryItemEntity(
                        wrapperType = request.wrapperType,
                        collectionId = request.collectionId,
                        artistId = request.artistId,
                        artistName = request.artistName,
                        collectionName = request.collectionName,
                        releaseDate = request.releaseDate
                    )
                }

                is AddArtistToLibraryRequest -> {
                    return LibraryItemEntity(
                        wrapperType = request.wrapperType,
                        artistId = request.artistId,
                        artistName = request.artistName,
                        primaryGenreName = request.primaryGenreName,
                    )
                }

                is AddSongToLibraryRequest -> {
                    return LibraryItemEntity(
                        wrapperType = request.wrapperType,
                        artistId = request.artistId,
                        artistName = request.artistName,
                        primaryGenreName = request.primaryGenreName,
                        kind = request.kind,
                        trackId = request.trackId,
                        trackName = request.trackName,
                        trackTimeMillis = request.trackTimeMillis,
                    )
                }
            }

        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LibraryItemEntity) return false

        if (wrapperType != other.wrapperType) return false
        if (collectionId != other.collectionId) return false
        if (artistId != other.artistId) return false
        if (artistName != other.artistName) return false
        if (collectionName != other.collectionName) return false
        if (releaseDate != other.releaseDate) return false
        if (primaryGenreName != other.primaryGenreName) return false
        if (kind != other.kind) return false
        if (trackId != other.trackId) return false
        if (trackName != other.trackName) return false
        if (trackTimeMillis != other.trackTimeMillis) return false

        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
