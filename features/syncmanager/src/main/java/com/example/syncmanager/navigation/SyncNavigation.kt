package com.example.syncmanager.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.syncmanager.ui.SyncManagerScreen

fun NavGraphBuilder.syncManagerGraph(onNavigateBack: () -> Unit, onSyncAll: () -> Unit = {}) {
    composable(SyncRoute.SYNC_MANAGER) {
        SyncManagerScreen(onNavigateBack = onNavigateBack, onSyncAll = onSyncAll)
    }
}
