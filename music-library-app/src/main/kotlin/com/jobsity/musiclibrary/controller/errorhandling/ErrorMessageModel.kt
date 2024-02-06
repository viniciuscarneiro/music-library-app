package com.jobsity.musiclibrary.controller.errorhandling

data class ErrorMessageModel(
    val timestamp: Long,
    val status: Int,
    val error: String,
    val message: String? = null,
)

data class ValidationErrorResponse(
    val status: Int,
    val error: String,
    val details: Map<String, String>,
)