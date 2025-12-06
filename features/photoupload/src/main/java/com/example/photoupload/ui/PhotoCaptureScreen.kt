package com.example.photoupload.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCaptureScreen(
    onNavigateBack: () -> Unit = {},
    onSave: () -> Unit = {},
    contextId: String,
    onPhotoCaptured: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add Photo",
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
                        Icon(Icons.AutoMirrored.Filled.ArrowBackIos, "Back")
                    }
                },
                actions = {
                    // Empty action to balance the title centering
                    Spacer(modifier = Modifier.width(48.dp))
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF111418),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
            )
        },
        containerColor = Color(0xFF111418)
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF111418))
        ) {
            // Main Image Preview (Mocked)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
                    .background(Color.Black)
            ) {
                AsyncImage(
                    model =
                        "https://lh3.googleusercontent.com/aida-public/AB6AXuDOZp-jjmBN2LUj4dWeAYjawUUHksBL-XIUAumFe4mbTtwT7G2542ub0k-GiSVIrwJ-_JZTBZ0WGJ28fB_LabXwu8McGeft3ZT3D3WAtmt3yzGovro90JKCLu15i7sJ_Uh8LRV-KuJW-SDIWtY6hl5zzaBRBFn9rdapy3mjj_Gwdo4T3HnFQeDBg08gMvWmcsiCK1NzMq5gP23toNMUP8nlNCJDm3haQAkBakKqwq4hfXs32CU0APXqTD5KxXI4GPDQ9laJ3Rh_xw",
                    contentDescription = "Captured Photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // File Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Filename Row
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .heightIn(min = 56.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF283039)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = "local_2fe10.jpeg",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color(0xFF283039)
                )

                // Sync Toggle Row
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .heightIn(min = 56.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Sync over Wi-Fi only",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )

                    var checked by remember { mutableStateOf(true) }
                    Switch(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        colors =
                            SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF137FEC), // Primary Blue
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color(0xFF283039)
                            )
                    )
                }

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { /* Retake */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF283039),
                                contentColor = Color.White
                            )
                    ) { Text("Retake", fontWeight = FontWeight.Bold) }

                    Button(
                        onClick = onSave,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF137FEC), // Primary Blue
                                contentColor = Color.White
                            )
                    ) { Text("Save", fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}
