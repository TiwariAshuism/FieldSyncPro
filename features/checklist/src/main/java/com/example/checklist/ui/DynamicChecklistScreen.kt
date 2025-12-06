package com.example.checklist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.checklist.data.ChecklistRepository
import com.example.checklist.domain.SubmitChecklistUseCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicChecklistScreen(jobId: String, onNavigateBack: () -> Unit = {}) {
    // Manual DI for now - in production use Hilt
    val repository = remember { ChecklistRepository() }
    val useCase = remember { SubmitChecklistUseCase(repository) }
    val viewModel = remember { ChecklistViewModel(useCase) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(jobId) { viewModel.loadChecklist(jobId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "HVAC Unit Inspection",
                        style =
                            MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBackIos, "Back")
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
            )
        },
        bottomBar = {
            ChecklistStickyFooter(
                onSave = { /* Save progress */ },
                onComplete = { /* Complete checklist */ }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (val state = uiState) {
                is ChecklistUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is ChecklistUiState.Success -> {
                    ChecklistContent(
                        checklist = state.checklist,
                        onAnswerChanged = viewModel::onAnswerChanged,
                        onPhotoAdded = viewModel::onPhotoAdded
                    )
                }

                is ChecklistUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@Composable
private fun ChecklistContent(
    checklist: com.example.checklist.data.Checklist,
    onAnswerChanged: (String, String) -> Unit,
    onPhotoAdded: (String, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp) // Space for sticky footer
    ) {
        // Meta Text
        item {
            Text(
                text = "Saved Locally — Not Synced",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        checklist.sections.forEach { section ->
            item {
                Text(
                    text = section.title,
                    style =
                        MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }

            item {
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        section.items.forEachIndexed { index, item ->
                            ChecklistItemRow(
                                item = item,
                                onValueChanged = { newValue ->
                                    onAnswerChanged(item.id, newValue)
                                },
                                onPhotoAdded = { photoUrl -> onPhotoAdded(item.id, photoUrl) }
                            )
                            if (index < section.items.size - 1) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color =
                                        MaterialTheme.colorScheme.surfaceVariant.copy(
                                            alpha = 0.5f
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChecklistStickyFooter(onSave: () -> Unit, onComplete: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.9f),
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onSave,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                shape = RoundedCornerShape(50)
            ) { Text("Save Progress", fontWeight = FontWeight.Bold) }

            Button(
                onClick = onComplete,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                shape = RoundedCornerShape(50)
            ) { Text("Complete Checklist", fontWeight = FontWeight.Bold) }
        }
    }
}
