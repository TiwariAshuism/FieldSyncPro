package com.example.dailyjobs.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.dailyjobs.ui.DailyJobsListScreen

fun NavGraphBuilder.dailyJobsGraph(onJobClick: (String) -> Unit) {
    composable(DailyJobsRoute.DAILY_JOBS_LIST) { DailyJobsListScreen(onJobClick = onJobClick) }
}
