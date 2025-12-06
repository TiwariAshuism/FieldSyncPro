package com.example.jobdetails.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jobdetails.data.JobTab

/** Main screen for job details Displays job information, tabs, and serviceable assets */
@Composable
fun JobDetailsScreen(
        jobId: String,
        viewModel: JobDetailsViewModel,
        onNavigateBack: () -> Unit,
        onAssetClick: (String) -> Unit = {},
        onChecklistClick: () -> Unit = {},
        onStartInspection: () -> Unit = {},
        onSyncClick: () -> Unit = {}
) {
    // Collect state from ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val isOffline by viewModel.isOffline.collectAsStateWithLifecycle()

    // Load job details on first composition
    LaunchedEffect(jobId) { viewModel.loadJobDetails(jobId) }

    JobDetailsContent(
            uiState = uiState,
            selectedTab = selectedTab,
            isOffline = isOffline,
            onNavigateBack = onNavigateBack,
            onTabSelected = viewModel::onTabSelected,
            onGetDirectionsClick = {
                (uiState as? JobDetailsUiState.Success)?.let {
                    viewModel.onGetDirectionsClick(it.jobDetails.location)
                }
            },
            onAssetClick = { assetId ->
                viewModel.onAssetClick(assetId)
                onAssetClick(assetId)
            },
            onStartInspectionClick = {
                viewModel.onStartInspectionClick(jobId)
                onStartInspection()
            },
            onChecklistClick = onChecklistClick,
            onSyncClick = onSyncClick
    )
}

/** Content composable that handles different UI states */
@Composable
private fun JobDetailsContent(
        uiState: JobDetailsUiState,
        selectedTab: JobTab,
        isOffline: Boolean,
        onNavigateBack: () -> Unit,
        onTabSelected: (JobTab) -> Unit,
        onGetDirectionsClick: () -> Unit,
        onAssetClick: (String) -> Unit,
        onStartInspectionClick: () -> Unit,
        onChecklistClick: () -> Unit,
        onSyncClick: () -> Unit
) {
    Scaffold(
            topBar = {
                Column {
                    JobDetailsTopBar(
                            onNavigateBack = onNavigateBack,
                            onMoreClick = { /* Handle more options */},
                            onSyncClick = onSyncClick
                    )

                    // Show offline banner if offline
                    if (isOffline) {
                        OfflineBanner()
                    }

                    // Tab row
                    JobDetailsTabRow(selectedTab = selectedTab, onTabSelected = onTabSelected)
                }
            },
            bottomBar = { StartInspectionButton(onClick = onStartInspectionClick) }
    ) { paddingValues ->
        when (uiState) {
            is JobDetailsUiState.Loading -> {
                LoadingContent(modifier = Modifier.padding(paddingValues))
            }
            is JobDetailsUiState.Success -> {
                SuccessContent(
                        selectedTab = selectedTab,
                        jobDetails = uiState.jobDetails,
                        assets = uiState.assets,
                        onGetDirectionsClick = onGetDirectionsClick,
                        onAssetClick = onAssetClick,
                        onChecklistClick = onChecklistClick,
                        modifier = Modifier.padding(paddingValues)
                )
            }
            is JobDetailsUiState.Error -> {
                ErrorContent(message = uiState.message, modifier = Modifier.padding(paddingValues))
            }
        }
    }
}

/** Loading state content */
@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

/** Success state content showing job details based on selected tab */
@Composable
private fun SuccessContent(
        selectedTab: JobTab,
        jobDetails: com.example.jobdetails.data.JobDetails,
        assets: List<com.example.jobdetails.data.ServiceableAsset>,
        onGetDirectionsClick: () -> Unit,
        onAssetClick: (String) -> Unit,
        onChecklistClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    LazyColumn(
            modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        when (selectedTab) {
            JobTab.OVERVIEW -> {
                // Job Summary Card
                item {
                    JobSummaryCard(
                            jobDetails = jobDetails,
                            onGetDirectionsClick = onGetDirectionsClick
                    )
                }

                // Serviceable Assets Section
                item {
                    Text(
                            text = "Serviceable Assets",
                            style =
                                    MaterialTheme.typography.titleLarge.copy(
                                            fontWeight =
                                                    androidx.compose.ui.text.font.FontWeight.Bold,
                                            fontSize = 18.sp
                                    ),
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }

                item { ServiceableAssetsList(assets = assets, onAssetClick = onAssetClick) }
            }
            JobTab.CHECKLIST -> {
                item {
                    PlaceholderTabContent(
                            title = "Checklist",
                            description = "Task checklist will be displayed here",
                            onActionClick = onChecklistClick
                    )
                }
            }
            JobTab.ASSETS -> {
                item { AssetList(assets = assets, onAssetClick = onAssetClick) }
            }
            JobTab.PHOTOS -> {
                item {
                    PlaceholderTabContent(
                            title = "Photos",
                            description = "Job photos will be displayed here",
                            actionText = "Add Photo"
                    )
                }
            }
            JobTab.NOTES -> {
                item {
                    PlaceholderTabContent(
                            title = "Notes",
                            description = "Job notes will be displayed here",
                            actionText = "Add Note"
                    )
                }
            }
        }
    }
}

/** Error state content */
@Composable
private fun ErrorContent(message: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                    text = "Error",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
            )
            Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** Placeholder content for tabs that are not yet implemented */
@Composable
private fun PlaceholderTabContent(
        title: String,
        description: String,
        actionText: String = "View Details",
        onActionClick: () -> Unit = {},
        modifier: Modifier = Modifier
) {
    Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                    text = title,
                    style =
                            MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
            )
            Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Button(onClick = onActionClick) { Text(actionText) }
        }
    }
}
