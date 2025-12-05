package com.example.jobdetails.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailsScreen(
        jobId: String,
        onNavigateBack: () -> Unit = {},
        onAssetClick: (String) -> Unit = {},
        onChecklistClick: () -> Unit = {}
) {
    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("Job Details") },
                        navigationIcon = { IconButton(onClick = onNavigateBack) { Text("←") } }
                )
            }
    ) { paddingValues ->
        Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Job #$jobId", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Status: In Progress")
                    Text(text = "Priority: High")
                    Text(text = "Assigned Date: Today")
                }
            }

            Button(onClick = onChecklistClick, modifier = Modifier.fillMaxWidth()) {
                Text("Open Checklist")
            }

            Text(text = "Assets", style = MaterialTheme.typography.titleMedium)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf("Asset-001", "Asset-002", "Asset-003")) { asset ->
                    Card(onClick = { onAssetClick(asset) }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = asset, modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}
