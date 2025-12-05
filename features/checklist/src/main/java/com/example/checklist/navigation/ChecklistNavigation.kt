package com.example.checklist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.checklist.ui.DynamicChecklistScreen

fun NavGraphBuilder.checklistGraph(onNavigateBack: () -> Unit) {
    composable(
            route = ChecklistRoute.CHECKLIST,
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
    ) { backStackEntry ->
        val jobId = backStackEntry.arguments?.getString("jobId") ?: ""
        DynamicChecklistScreen(jobId = jobId, onNavigateBack = onNavigateBack)
    }
}
