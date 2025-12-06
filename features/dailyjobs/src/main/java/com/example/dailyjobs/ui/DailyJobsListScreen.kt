package com.example.dailyjobs.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core_ui.theme.JobStatus
import com.example.core_ui.theme.getStatusColorForJob

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyJobsListScreen(
    onJobClick: (String) -> Unit = {},
    viewModel: DailyJobsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            DailyJobsTopBar(
                isOnline =
                    when (val state = uiState) {
                        is DailyJobsUiState.Success -> state.isOnline
                        else -> false
                    }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = 0,
                onTabSelected = { /* TODO: Handle tab selection */ }
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is DailyJobsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }

            is DailyJobsUiState.Success -> {
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(state.jobs) { job ->
                        JobCard(
                            job = job,
                            onJobClick = { onJobClick(job.id) },
                            onStartJob = { viewModel.onStartJob(job.id) }
                        )
                    }
                }
            }

            is DailyJobsUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) { Text(text = state.message, color = MaterialTheme.colorScheme.error) }
            }
        }
    }
}

@Composable
fun DailyJobsTopBar(isOnline: Boolean) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .statusBarsPadding()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Online Status
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color =
                    if (isOnline) Color(0xFF34C759).copy(alpha = 0.2f)
                    else Color(0xFF8A8A8E).copy(alpha = 0.2f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isOnline) Color(0xFF34C759)
                                    else Color(0xFF8A8A8E)
                                )
                    )
                    Text(
                        text = if (isOnline) "Online" else "Offline",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isOnline) Color(0xFF34C759) else Color(0xFF8A8A8E),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Title
        Text(
            text = "Today's Work Orders",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun JobCard(job: Job, onJobClick: () -> Unit, onStartJob: () -> Unit) {
    val statusColor = getStatusColorForJob(job.status)

    Card(
        onClick = onJobClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title and Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = statusColor.backgroundColor
                ) {
                    Text(
                        text = statusColor.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor.textColor,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            // Location with Sync Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = job.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (!job.isSynced) {
                    Icon(
                        imageVector = Icons.Default.CloudOff,
                        contentDescription = "Unsynced",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Action Button
            val buttonText =
                when (job.status) {
                    JobStatus.PENDING -> "Start Job"
                    JobStatus.IN_PROGRESS, JobStatus.COMPLETED -> "View Details"
                }

            if (job.status == JobStatus.COMPLETED) {
                OutlinedButton(
                    onClick = onJobClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) { Text(buttonText) }
            } else {
                Button(
                    onClick = if (job.status == JobStatus.PENDING) onStartJob else onJobClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) { Text(buttonText) }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Jobs")
            },
            label = { Text("Jobs") }
        )

        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = { Icon(imageVector = Icons.Default.Map, contentDescription = "Map") },
            label = { Text("Map") }
        )

        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) },
            icon = {
                Icon(imageVector = Icons.Default.Inventory, contentDescription = "Assets")
            },
            label = { Text("Assets") }
        )

        NavigationBarItem(
            selected = selectedTab == 3,
            onClick = { onTabSelected(3) },
            icon = {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile")
            },
            label = { Text("Profile") }
        )
    }
}
