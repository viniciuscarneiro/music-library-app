package com.jobsity.musiclibrary.controller.request

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.jobsity.musiclibrary.thridparty.itunes.WrapperType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "wrapperType", visible = true)
sealed class AddItemToLibraryRequest(
    @get:NotNull
    open val wrapperType: WrapperType,
    @get:NotNull
    open val artistId: Int,
    @get:NotBlank
    @get:Size(max = 256)
    open val artistName: String,
)

@JsonTypeName("COLLECTION")
data class AddAlbumToLibraryRequest(
    @get:NotNull
    override val wrapperType: WrapperType,
    @get:NotNull
    override val artistId: Int,
    @get:NotNull
    val collectionId: Int,
    @get:NotBlank
    @get:Size(max = 256)
    override val artistName: String,
    @get:NotBlank
    @get:Size(max = 256)
    val collectionName: String?,
    @get:NotNull
    val releaseDate: String,
) : AddItemToLibraryRequest(wrapperType, artistId, artistName)

@JsonTypeName("ARTIST")
data class AddArtistToLibraryRequest(
    @get:NotNull
    override val wrapperType: WrapperType,
    @get:NotNull
    override val artistId: Int,
    @get:NotBlank
    @get:Size(max = 256)
    override val artistName: String,
    @get:NotBlank
    @get:Size(max = 64)
    val primaryGenreName: String,
) : AddItemToLibraryRequest(wrapperType, artistId, artistName)

@JsonTypeName("TRACK")
data class AddSongToLibraryRequest(
    @get:NotNull
    override val wrapperType: WrapperType,
    @get:NotNull
    override val artistId: Int,
    @get:NotBlank
    @get:Size(max = 256)
    override val artistName: String,
    @get:NotBlank
    @get:Size(max = 32)
    val kind: String,
    @get:NotNull
    val trackId: Int,
    @get:NotNull
    val collectionId: Int,
    @get:NotBlank
    @get:Size(max = 256)
    val trackName: String,
    @get:NotBlank
    @get:Size(max = 256)
    val collectionName: String,
    @get:NotBlank
    @get:Size(max = 64)
    val primaryGenreName: String,
    @get:NotNull
    val releaseDate: String,
    @get:NotNull
    val trackTimeMillis: Long,
) : AddItemToLibraryRequest(wrapperType, artistId, artistName)