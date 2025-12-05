package com.example.checklist.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicChecklistScreen(jobId: String, onNavigateBack: () -> Unit = {}) {
    val checklistItems = remember {
        mutableStateListOf(
                "Check equipment" to false,
                "Take photos" to false,
                "Update status" to false
        )
    }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("Checklist - Job $jobId") },
                        navigationIcon = { IconButton(onClick = onNavigateBack) { Text("←") } }
                )
            }
    ) { paddingValues ->
        LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(checklistItems) { (item, checked) ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item)
                        Checkbox(
                                checked = checked,
                                onCheckedChange = { isChecked ->
                                    val index = checklistItems.indexOf(item to checked)
                                    checklistItems[index] = item to isChecked
                                }
                        )
                    }
                }
            }
        }
    }
}
