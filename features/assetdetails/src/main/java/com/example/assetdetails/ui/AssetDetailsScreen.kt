package com.example.assetdetails.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetDetailsScreen(
        assetId: String,
        onNavigateBack: () -> Unit = {},
        onTakePhoto: () -> Unit = {}
) {
    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("Asset Details") },
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
                    Text(text = "Asset ID: $assetId", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Type: Equipment")
                    Text(text = "Status: Active")
                    Text(text = "Location: Field Site A")
                }
            }

            Button(onClick = onTakePhoto, modifier = Modifier.fillMaxWidth()) { Text("Take Photo") }
        }
    }
}
