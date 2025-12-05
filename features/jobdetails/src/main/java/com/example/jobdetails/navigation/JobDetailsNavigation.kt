package com.example.jobdetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jobdetails.ui.JobDetailsScreen

fun NavGraphBuilder.jobDetailsGraph(
        onNavigateBack: () -> Unit,
        onAssetClick: (String) -> Unit,
        onChecklistClick: (String) -> Unit
) {
    composable(
            route = JobDetailsRoute.JOB_DETAILS,
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
    ) { backStackEntry ->
        val jobId = backStackEntry.arguments?.getString("jobId") ?: ""
        JobDetailsScreen(
                jobId = jobId,
                onNavigateBack = onNavigateBack,
                onAssetClick = onAssetClick,
                onChecklistClick = { onChecklistClick(jobId) }
        )
    }
}
