package com.jobsity.musiclibrary.model

import com.jobsity.musiclibrary.controller.request.CreateLibraryRequest
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "libraries")
class LibraryEntity(
    @Id
    @MongoId
    val id: String? = null,
    val name: String,

    ) {
    companion object {
        fun from(createLibraryRequest: CreateLibraryRequest): LibraryEntity {
            return LibraryEntity(name = createLibraryRequest.name)
        }
    }

    @DBRef
    var libraryItems: MutableSet<LibraryItemEntity> = mutableSetOf()
}
