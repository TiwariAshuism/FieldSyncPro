package com.example.syncmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class SyncItem(val name: String, val status: String, val count: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyncManagerScreen(onNavigateBack: () -> Unit = {}, onSyncAll: () -> Unit = {}) {
    val syncItems =
            listOf(
                    SyncItem("Jobs", "Synced", 15),
                    SyncItem("Assets", "Pending", 8),
                    SyncItem("Checklists", "Synced", 22),
                    SyncItem("Photos", "Uploading", 5)
            )

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("Sync Manager") },
                        navigationIcon = { IconButton(onClick = onNavigateBack) { Text("←") } }
                )
            }
    ) { paddingValues ->
        Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = onSyncAll, modifier = Modifier.fillMaxWidth()) { Text("Sync All") }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(syncItems) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                                Text(
                                        text = "Count: ${item.count}",
                                        style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Text(text = item.status, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
