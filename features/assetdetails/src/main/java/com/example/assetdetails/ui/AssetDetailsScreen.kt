package com.example.assetdetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.assetdetails.data.AssetDetails
import com.example.assetdetails.data.AssetDetailsRepository
import com.example.assetdetails.data.InspectionRecord
import com.example.assetdetails.data.InspectionStatus
import com.example.assetdetails.domain.GetAssetDetailsUseCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetDetailsScreen(
    assetId: String,
    onNavigateBack: () -> Unit = {},
    onTakePhoto: () -> Unit = {}
) {
    // Manual DI for now
    val repository = remember { AssetDetailsRepository() }
    val useCase = remember { GetAssetDetailsUseCase(repository) }
    val viewModel = remember { AssetDetailsViewModel(useCase) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(assetId) { viewModel.loadAssetDetails(assetId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Asset Details",
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
                actions = {
                    IconButton(onClick = { /* Scan QR */ }) {
                        Icon(Icons.Default.QrCodeScanner, "Scan QR")
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor =
                            MaterialTheme.colorScheme.background
                    )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (val state = uiState) {
                is AssetDetailsUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                }

                is AssetDetailsUiState.Success -> {
                    AssetDetailsContent(
                        assetDetails = state.assetDetails,
                        onTakePhoto = onTakePhoto
                    )
                }

                is AssetDetailsUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AssetDetailsContent(assetDetails: AssetDetails, onTakePhoto: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Header Image
        item {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(240.dp)
                        .clip(RoundedCornerShape(12.dp))
            ) {
                AsyncImage(
                    model = assetDetails.imageUrl,
                    contentDescription = "Asset Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Description List
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                AssetInfoRow("Model", assetDetails.model)
                AssetInfoRow("Serial Number", assetDetails.serialNumber)
                AssetInfoRow(
                    "Last Inspection",
                    assetDetails.lastInspectionDate.toString()
                )
            }
        }

        // Inspection History Header
        item {
            Text(
                text = "Inspection History",
                style =
                    MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                modifier =
                    Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 32.dp,
                        bottom = 8.dp
                    )
            )
        }

        // Inspection History List
        items(assetDetails.inspectionHistory) { record -> InspectionHistoryItem(record) }

        // Action Buttons
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { /* Add Observation */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                MaterialTheme.colorScheme.primary
                        )
                ) {
                    Icon(
                        Icons.Default.AddComment,
                        null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Observation", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onTakePhoto,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        ButtonDefaults.outlinedButtonColors(
                            contentColor =
                                MaterialTheme.colorScheme.primary
                        ),
                    border =
                        androidx.compose.foundation.BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.2f
                            )
                        )
                ) {
                    Icon(
                        Icons.Default.UploadFile,
                        null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Upload Image", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun AssetInfoRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 12.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun InspectionHistoryItem(record: InspectionRecord) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Status Icon
            val (icon, color, containerColor) =
                when (record.status) {
                    InspectionStatus.PASSED ->
                        Triple(
                            Icons.Default.CheckCircle,
                            Color(0xFF22C55E), // Green-500
                            Color(0xFFDCFCE7) // Green-100
                        )

                    InspectionStatus.WARNING ->
                        Triple(
                            Icons.Default.Warning,
                            Color(0xFFF59E0B), // Amber-500
                            Color(0xFFFEF3C7) // Amber-100
                        )

                    InspectionStatus.FAILED ->
                        Triple(
                            Icons.Default.Cancel,
                            Color(0xFFEF4444), // Red-500
                            Color(0xFFFEE2E2) // Red-100
                        )
                }

            Box(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(containerColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column {
                Text(
                    text = record.summary,
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium
                        ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
                Text(
                    text = "Performed by ${record.performedBy}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Text(
            text = record.date.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
