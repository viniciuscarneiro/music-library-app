package com.jobsity.musiclibrary.repository

import com.jobsity.musiclibrary.model.LibraryEntity
import org.springframework.data.repository.CrudRepository

interface LibraryRepository : CrudRepository<LibraryEntity, String> {
    fun findLibraryEntityById(id: String): LibraryEntity?
}