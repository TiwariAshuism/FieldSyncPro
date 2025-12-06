package com.example.jobdetails.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jobdetails.data.JobDetailsRepository
import com.example.jobdetails.domain.GetJobDetailsUseCase
import com.example.jobdetails.ui.JobDetailsScreen
import com.example.jobdetails.ui.JobDetailsViewModel

fun NavGraphBuilder.jobDetailsGraph(
        onNavigateBack: () -> Unit,
        onAssetClick: (String) -> Unit,
        onChecklistClick: (String) -> Unit,
        onSyncClick: () -> Unit
) {
        composable(
                route = JobDetailsRoute.JOB_DETAILS,
                arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
                val jobId = backStackEntry.arguments?.getString("jobId") ?: ""

                // Create ViewModel with dependencies
                // In a real app, use Hilt/Dagger for dependency injection
                val repository = JobDetailsRepository()
                val useCase = GetJobDetailsUseCase(repository)
                val viewModel: JobDetailsViewModel =
                        viewModel(
                                factory =
                                        object : androidx.lifecycle.ViewModelProvider.Factory {
                                                override fun <
                                                        T : androidx.lifecycle.ViewModel> create(
                                                        modelClass: Class<T>
                                                ): T {
                                                        @Suppress("UNCHECKED_CAST")
                                                        return JobDetailsViewModel(useCase) as T
                                                }
                                        }
                        )

                JobDetailsScreen(
                        jobId = jobId,
                        viewModel = viewModel,
                        onNavigateBack = onNavigateBack,
                        onAssetClick = onAssetClick,
                        onChecklistClick = { onChecklistClick(jobId) },
                        onStartInspection = { onChecklistClick(jobId) },
                        onSyncClick = onSyncClick
                )
        }
}
