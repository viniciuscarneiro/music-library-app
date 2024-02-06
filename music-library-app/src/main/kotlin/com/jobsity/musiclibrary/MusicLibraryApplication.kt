package com.jobsity.musiclibrary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MusicLibraryApplication

fun main(args: Array<String>) {
	runApplication<MusicLibraryApplication>(*args)
}
