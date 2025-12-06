package com.example.jobdetails.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jobdetails.data.ServiceableAsset

/**
 * Composable for displaying a list of serviceable assets Can be used in the Assets tab or any other
 * context
 */
@Composable
fun AssetList(
    assets: List<ServiceableAsset>,
    onAssetClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "All Assets",
            style =
                MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ServiceableAssetsList(assets = assets, onAssetClick = onAssetClick)

        // Asset summary
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Assets",
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium
                        ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "${assets.size}",
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
