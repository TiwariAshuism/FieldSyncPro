package com.example.photoupload.navigation

object PhotoRoute {
    const val PHOTO_CAPTURE = "photo_capture/{contextId}"

    fun createRoute(contextId: String = "default") = "photo_capture/$contextId"
}
