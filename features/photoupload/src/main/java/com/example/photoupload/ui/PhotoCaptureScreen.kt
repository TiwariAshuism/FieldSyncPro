package com.example.photoupload.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCaptureScreen(
        contextId: String,
        onNavigateBack: () -> Unit = {},
        onPhotoCaptured: () -> Unit = {}
) {
    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("Photo Capture") },
                        navigationIcon = { IconButton(onClick = onNavigateBack) { Text("←") } }
                )
            }
    ) { paddingValues ->
        Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            Card(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Camera Preview Placeholder")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onPhotoCaptured, modifier = Modifier.fillMaxWidth()) {
                Text("Capture Photo")
            }

            OutlinedButton(onClick = onNavigateBack, modifier = Modifier.fillMaxWidth()) {
                Text("Cancel")
            }
        }
    }
}
