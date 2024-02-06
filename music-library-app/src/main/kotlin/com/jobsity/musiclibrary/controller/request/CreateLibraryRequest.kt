package com.jobsity.musiclibrary.controller.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateLibraryRequest(
    @get:NotBlank
    @get:Size(max = 256)
    val name: String,
)