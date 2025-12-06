package com.example.jobdetails.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.ElectricalServices
import androidx.compose.material.icons.outlined.WindPower
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.jobdetails.data.JobDetails
import com.example.jobdetails.data.JobTab
import com.example.jobdetails.data.ServiceableAsset

/** Top app bar for job details screen */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailsTopBar(
        onNavigateBack: () -> Unit,
        onMoreClick: () -> Unit,
        onSyncClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    TopAppBar(
            title = {
                Text(
                        text = "Job Details",
                        style =
                                MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            actions = {
                IconButton(onClick = onSyncClick) {
                    Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Sync,
                            contentDescription = "Sync",
                            tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(onClick = onMoreClick) {
                    Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "More options",
                            tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            colors =
                    TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                    ),
            modifier = modifier
    )
}

/** Offline mode indicator banner */
@Composable
fun OfflineBanner(modifier: Modifier = Modifier) {
    Row(
            modifier =
                    modifier.fillMaxWidth()
                            .background(Color(0xFFFF9500).copy(alpha = 0.2f))
                            .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
                imageVector = androidx.compose.material.icons.Icons.Outlined.CloudOff,
                contentDescription = "Offline",
                tint = Color(0xFFFF9500),
                modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
                text = "Offline Mode Active",
                style =
                        MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFFF9500)
                        )
        )
    }
}

/** Custom tab row for job details */
@Composable
fun JobDetailsTabRow(
        selectedTab: JobTab,
        onTabSelected: (JobTab) -> Unit,
        modifier: Modifier = Modifier
) {
    ScrollableTabRow(
            selectedTabIndex = selectedTab.ordinal,
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                if (tabPositions.isNotEmpty() && selectedTab.ordinal < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                            modifier =
                                    Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                            height = 3.dp,
                            color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            divider = {
                HorizontalDivider(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            },
            edgePadding = 16.dp
    ) {
        JobTab.entries.forEach { tab ->
            Tab(
                    selected = selectedTab == tab,
                    onClick = { onTabSelected(tab) },
                    text = {
                        Text(
                                text = tab.title,
                                style =
                                        MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                        ),
                                color =
                                        if (selectedTab == tab) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        }
                        )
                    },
                    modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}

/** Job summary card with map and details */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JobSummaryCard(
        jobDetails: JobDetails,
        onGetDirectionsClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // Map image
            AsyncImage(
                    model = jobDetails.mapImageUrl,
                    contentDescription = "Map showing the location of the job site",
                    modifier =
                            Modifier.fillMaxWidth()
                                    .height(144.dp)
                                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop
            )

            Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                        text = "Job Summary",
                        style =
                                MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                ),
                        color = MaterialTheme.colorScheme.onSurface
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    JobDetailRow(label = "Customer:", value = jobDetails.customer)
                    JobDetailRow(label = "Location:", value = jobDetails.location)
                    JobDetailRow(
                            label = "Scheduled:",
                            value = jobDetails.getFormattedScheduledTime()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                            text = jobDetails.getLastSyncedText(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Button(
                            onClick = onGetDirectionsClick,
                            colors =
                                    ButtonDefaults.buttonColors(
                                            containerColor =
                                                    MaterialTheme.colorScheme.primary.copy(
                                                            alpha = 0.2f
                                                    ),
                                            contentColor = MaterialTheme.colorScheme.primary
                                    ),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                                text = "Get Directions",
                                style =
                                        MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 14.sp
                                        )
                        )
                    }
                }
            }
        }
    }
}

/** Helper composable for job detail rows */
@Composable
private fun JobDetailRow(label: String, value: String) {
    Text(
            text =
                    buildAnnotatedString {
                        withStyle(
                                style =
                                        SpanStyle(
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.onSurface
                                        )
                        ) { append(label) }
                        append(" ")
                        append(value)
                    },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

/** Individual serviceable asset item */
@Composable
fun ServiceableAssetItem(
        asset: ServiceableAsset,
        onClick: () -> Unit,
        showDivider: Boolean = true,
        modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Surface(onClick = onClick, color = Color.Transparent, modifier = Modifier.fillMaxWidth()) {
            Row(
                    modifier =
                            Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icon container
                    Surface(
                            modifier = Modifier.size(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                    imageVector = getAssetIcon(asset.iconName),
                                    contentDescription = asset.name,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Column {
                        Text(
                                text = asset.name,
                                style =
                                        MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.Medium
                                        ),
                                color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                                text = "ID: ${asset.assetId}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Icon(
                        imageVector =
                                androidx.compose.material.icons.Icons.AutoMirrored.Filled
                                        .ArrowBackIos,
                        contentDescription = "View asset",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (showDivider) {
            HorizontalDivider(
                    modifier = Modifier.padding(start = 80.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

/** List of serviceable assets */
@Composable
fun ServiceableAssetsList(
        assets: List<ServiceableAsset>,
        onAssetClick: (String) -> Unit,
        modifier: Modifier = Modifier
) {
    Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            assets.forEachIndexed { index, asset ->
                ServiceableAssetItem(
                        asset = asset,
                        onClick = { onAssetClick(asset.assetId) },
                        showDivider = index < assets.size - 1
                )
            }
        }
    }
}

/** Start inspection button (sticky footer) */
@Composable
fun StartInspectionButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
            tonalElevation = 8.dp
    ) {
        Column {
            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))

            Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth().padding(16.dp).height(48.dp),
                    colors =
                            ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                            ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                        text = "Start Inspection",
                        style =
                                MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                ),
                        color = Color.White
                )
            }
        }
    }
}

/** Helper function to get appropriate icon for asset type */
@Composable
private fun getAssetIcon(iconName: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when (iconName) {
        "ac_unit" -> androidx.compose.material.icons.Icons.Outlined.AcUnit
        "wind_power" -> androidx.compose.material.icons.Icons.Outlined.WindPower
        "electrical_services" -> androidx.compose.material.icons.Icons.Outlined.ElectricalServices
        else -> androidx.compose.material.icons.Icons.Outlined.Build
    }
}
