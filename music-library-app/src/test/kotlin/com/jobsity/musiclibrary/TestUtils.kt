package com.jobsity.musiclibrary

import com.fasterxml.jackson.databind.ObjectMapper

class TestUtils {
    companion object {
        fun convertObjectToJson(obj: Any): String {
            return ObjectMapper().writeValueAsString(obj)
        }
    }
}