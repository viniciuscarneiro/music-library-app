package com.jobsity.musiclibrary.error

class LibraryNotFoundException(message: String, relevantData: Map<String, Any?>) :
    InvalidInputDataException(message, relevantData)