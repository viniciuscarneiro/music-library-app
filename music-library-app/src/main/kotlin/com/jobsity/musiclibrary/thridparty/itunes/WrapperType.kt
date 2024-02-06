package com.jobsity.musiclibrary.thridparty.itunes

import com.jobsity.musiclibrary.error.InvalidWrapperTypeException

enum class WrapperType(val itunesName: String) {

    COLLECTION("collection"),
    TRACK("track"),
    ARTIST("artist");

    companion object {
        fun parse(wrapperType: String?): WrapperType {
            return wrapperType?.let {
                entries.firstOrNull { enumValue -> enumValue.itunesName == it }
                    ?: throw InvalidWrapperTypeException("Wrapper type '$it' is invalid.")
            } ?: throw IllegalArgumentException("Wrapper type cannot be null.")
        }
    }
}
