package com.example.syncmanager.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.syncmanager.ui.SyncManagerScreen

fun NavGraphBuilder.syncManagerGraph(onNavigateBack: () -> Unit, onSyncComplete: () -> Unit = {}) {
    composable(SyncRoute.SYNC_MANAGER) {
        SyncManagerScreen(onNavigateBack = onNavigateBack, isInitialSync = false)
    }

    composable(SyncRoute.INITIAL_SYNC) {
        SyncManagerScreen(
                onNavigateBack = onNavigateBack, // Or maybe disable back?
                isInitialSync = true,
                onSyncComplete = onSyncComplete
        )
    }
}
