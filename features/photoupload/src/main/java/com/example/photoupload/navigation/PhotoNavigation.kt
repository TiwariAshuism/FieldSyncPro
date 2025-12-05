package com.example.photoupload.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.photoupload.ui.PhotoCaptureScreen

fun NavGraphBuilder.photoUploadGraph(onNavigateBack: () -> Unit, onPhotoCaptured: () -> Unit) {
    composable(
            route = PhotoRoute.PHOTO_CAPTURE,
            arguments = listOf(navArgument("contextId") { type = NavType.StringType })
    ) { backStackEntry ->
        val contextId = backStackEntry.arguments?.getString("contextId") ?: "default"
        PhotoCaptureScreen(
                contextId = contextId,
                onNavigateBack = onNavigateBack,
                onPhotoCaptured = onPhotoCaptured
        )
    }
}
