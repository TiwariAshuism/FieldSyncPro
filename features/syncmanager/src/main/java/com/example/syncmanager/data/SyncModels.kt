package com.example.syncmanager.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Note
import androidx.compose.ui.graphics.vector.ImageVector

enum class SyncType(val title: String, val icon: ImageVector) {
    Forms("Forms", Icons.Default.Description),
    Checklists("Checklists", Icons.Default.CheckCircle),
    Images("Images", Icons.Default.Image),
    Notes("Notes", Icons.Default.Note)
}

enum class SyncStatus {
    Synced,
    Syncing,
    Failed,
    Pending
}

data class SyncItem(
        val id: String,
        val type: SyncType,
        val status: SyncStatus,
        val lastSynced: String? = null,
        val errorMessage: String? = null
)
