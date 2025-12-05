package com.example.assetdetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.assetdetails.ui.AssetDetailsScreen

fun NavGraphBuilder.assetDetailsGraph(onNavigateBack: () -> Unit, onTakePhoto: (String) -> Unit) {
    composable(
            route = AssetRoute.ASSET_DETAILS,
            arguments = listOf(navArgument("assetId") { type = NavType.StringType })
    ) { backStackEntry ->
        val assetId = backStackEntry.arguments?.getString("assetId") ?: ""
        AssetDetailsScreen(
                assetId = assetId,
                onNavigateBack = onNavigateBack,
                onTakePhoto = { onTakePhoto(assetId) }
        )
    }
}
