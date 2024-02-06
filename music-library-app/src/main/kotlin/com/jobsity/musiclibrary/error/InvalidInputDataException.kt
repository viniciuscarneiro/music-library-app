package com.jobsity.musiclibrary.error

open class InvalidInputDataException(
    message: String,
    private val relevantData: Map<String, Any?> = mapOf(),
) : RuntimeException(message) {
    fun extendedMessage(): String {
        return "$message, relevantData=$relevantData"
    }
}