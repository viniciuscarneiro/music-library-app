package com.jobsity.musiclibrary.error

class LibraryItemNotFoundException(message: String, relevantData: Map<String, Any?>) :
    InvalidInputDataException(message, relevantData)