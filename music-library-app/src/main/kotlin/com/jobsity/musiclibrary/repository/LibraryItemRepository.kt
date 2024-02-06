package com.jobsity.musiclibrary.repository

import com.jobsity.musiclibrary.model.LibraryItemEntity
import com.jobsity.musiclibrary.thridparty.itunes.WrapperType
import org.springframework.data.repository.CrudRepository

interface LibraryItemRepository : CrudRepository<LibraryItemEntity, String> {
    fun findByWrapperTypeAndCollectionIdAndArtistIdAndKindAndTrackId(
        wrapperType: WrapperType,
        collectionId: Int?,
        artistId: Int?,
        kind: String?,
        trackId: Int?,
    ): LibraryItemEntity?
}