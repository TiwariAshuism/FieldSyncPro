package com.example.syncmanager.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.syncmanager.data.SyncItem
import com.example.syncmanager.data.SyncStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyncManagerScreen(
        onNavigateBack: () -> Unit = {},
        viewModel: SyncManagerViewModel = viewModel(),
        isInitialSync: Boolean = false,
        onSyncComplete: () -> Unit = {}
) {
    val syncItems by viewModel.syncItems.collectAsStateWithLifecycle()

    LaunchedEffect(isInitialSync) {
        if (isInitialSync) {
            viewModel.onSyncNow()
        }
    }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = {
                            Text(
                                    if (isInitialSync) "Initial Sync" else "Sync Manager",
                                    style =
                                            MaterialTheme.typography.titleLarge.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp
                                            )
                            )
                        },
                        navigationIcon = {
                            if (!isInitialSync) {
                                IconButton(onClick = onNavigateBack) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBackIos, "Back")
                                }
                            }
                        },
                        colors =
                                TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.background
                                )
                )
            },
            bottomBar = {
                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .padding(16.dp)
                                        .background(MaterialTheme.colorScheme.background)
                ) {
                    if (isInitialSync) {
                        Button(
                                onClick = onSyncComplete,
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors =
                                        ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.primary
                                        )
                        ) { Text("Continue", fontWeight = FontWeight.Bold) }
                    } else {
                        Button(
                                onClick = { viewModel.onSyncNow() },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors =
                                        ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.primary
                                        )
                        ) {
                            Icon(
                                    Icons.Default.Sync,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sync Now", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
    ) { paddingValues ->
        LazyColumn(
                modifier =
                        Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                        text = "Sync Status",
                        style =
                                MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                ),
                        modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(syncItems) { item ->
                SyncItemRow(item = item, onRetry = { viewModel.onRetryItem(item.id) })
            }
        }
    }
}

@Composable
fun SyncItemRow(item: SyncItem, onRetry: () -> Unit) {
    Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
        ) {
            // Icon Container
            Box(
                    modifier =
                            Modifier.size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                            MaterialTheme.colorScheme.surfaceVariant.copy(
                                                    alpha = 0.5f
                                            )
                                    ),
                    contentAlignment = Alignment.Center
            ) {
                Icon(
                        imageVector = item.type.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                )
            }

            Column {
                Text(
                        text = item.type.title,
                        style =
                                MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Medium
                                ),
                        color = MaterialTheme.colorScheme.onSurface
                )

                when (item.status) {
                    SyncStatus.Synced -> {
                        Text(
                                text = "Last synced: ${item.lastSynced ?: "Unknown"}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    SyncStatus.Failed -> {
                        Text(
                                text = item.errorMessage ?: "Sync Failed",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                        )
                    }
                    SyncStatus.Syncing -> {
                        Text(
                                text = "Syncing...",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                        )
                    }
                    SyncStatus.Pending -> {
                        Text(
                                text = "Pending...",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Status Indicator / Action
        when (item.status) {
            SyncStatus.Synced -> {
                Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Synced",
                        tint = Color(0xFF22C55E), // Green
                        modifier = Modifier.size(24.dp)
                )
            }
            SyncStatus.Failed -> {
                Button(
                        onClick = onRetry,
                        colors =
                                ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        contentColor = MaterialTheme.colorScheme.error
                                ),
                        contentPadding =
                                androidx.compose.foundation.layout.PaddingValues(
                                        horizontal = 12.dp,
                                        vertical = 4.dp
                                ),
                        modifier = Modifier.height(32.dp)
                ) {
                    Icon(
                            Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Retry", fontSize = 12.sp)
                }
            }
            SyncStatus.Syncing -> {
                CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                )
            }
            SyncStatus.Pending -> {
                Icon(
                        Icons.Default.Sync,
                        contentDescription = "Pending",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                )
            }
        }
    }
    HorizontalDivider(
            modifier = Modifier.padding(start = 64.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    )
}
