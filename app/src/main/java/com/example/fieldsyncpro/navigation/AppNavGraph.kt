package com.example.fieldsyncpro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assetdetails.navigation.AssetRoute
import com.example.assetdetails.navigation.assetDetailsGraph
import com.example.auth.navigation.AuthRoute
import com.example.auth.navigation.authGraph
import com.example.checklist.navigation.ChecklistRoute
import com.example.checklist.navigation.checklistGraph
import com.example.dailyjobs.navigation.DailyJobsRoute
import com.example.dailyjobs.navigation.dailyJobsGraph
import com.example.jobdetails.navigation.JobDetailsRoute
import com.example.jobdetails.navigation.jobDetailsGraph
import com.example.photoupload.navigation.photoUploadGraph
import com.example.syncmanager.navigation.SyncRoute
import com.example.syncmanager.navigation.syncManagerGraph

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = AuthRoute.LOGIN) {
        // Firebase Test Screen (for testing only)
        //composable(FIREBASE_TEST_ROUTE) { FirebaseTestScreen() }

        // Auth module
        authGraph(
                onLoginSuccess = {
                    navController.navigate(SyncRoute.INITIAL_SYNC) {
                        popUpTo(AuthRoute.LOGIN) { inclusive = true }
                    }
                }
        )

        // Daily Jobs module
        dailyJobsGraph(
                onJobClick = { jobId -> navController.navigate(JobDetailsRoute.createRoute(jobId)) }
        )

        // Job Details module
        jobDetailsGraph(
                onNavigateBack = { navController.popBackStack() },
                onAssetClick = { assetId ->
                    navController.navigate(AssetRoute.createRoute(assetId))
                },
                onChecklistClick = { jobId ->
                    navController.navigate(ChecklistRoute.createRoute(jobId))
                },
                onSyncClick = { navController.navigate(SyncRoute.SYNC_MANAGER) }
        )

        // Asset Details module
        assetDetailsGraph(
                onNavigateBack = { navController.popBackStack() },
                onTakePhoto = { assetId ->
                    navController.navigate(AssetRoute.photoCapture(assetId))
                },
                onPhotoSaved = { navController.popBackStack() }
        )

        // Checklist module
        checklistGraph(onNavigateBack = { navController.popBackStack() })

        // Photo Upload module
        photoUploadGraph(
                onNavigateBack = { navController.popBackStack() },
                onPhotoCaptured = { navController.popBackStack() }
        )

        // Sync Manager module
        syncManagerGraph(
                onNavigateBack = { navController.popBackStack() },
                onSyncComplete = {
                    navController.navigate(DailyJobsRoute.DAILY_JOBS_LIST) {
                        popUpTo(SyncRoute.INITIAL_SYNC) { inclusive = true }
                    }
                }
        )
    }
}
