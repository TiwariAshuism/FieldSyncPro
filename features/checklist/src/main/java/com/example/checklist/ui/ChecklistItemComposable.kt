package com.example.checklist.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.checklist.data.ChecklistItem

@Composable
fun ChecklistItemRow(
    item: ChecklistItem,
    onValueChanged: (String) -> Unit,
    onPhotoAdded: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        when (item) {
            is ChecklistItem.CheckboxItem -> CheckboxItem(item, onValueChanged)
            is ChecklistItem.TextFieldItem -> TextFieldItem(item, onValueChanged)
            is ChecklistItem.NumberFieldItem -> NumberFieldItem(item, onValueChanged)
            is ChecklistItem.DropdownItem -> DropdownItem(item, onValueChanged)
            is ChecklistItem.PhotoItem -> PhotoItem(item, onPhotoAdded)
            is ChecklistItem.NoteItem -> NoteItem(item, onValueChanged)
            is ChecklistItem.MultiSelectItem -> MultiSelectItem(item, onValueChanged)
        }
    }
}

@Composable
private fun CheckboxItem(item: ChecklistItem.CheckboxItem, onCheckedChange: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = item.checked,
            onCheckedChange = { onCheckedChange(it.toString()) },
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
private fun TextFieldItem(item: ChecklistItem.TextFieldItem, onValueChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedTextField(
            value = item.value,
            onValueChange = onValueChange,
            placeholder = { Text(item.placeholder) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )
    }
}

@Composable
private fun NumberFieldItem(item: ChecklistItem.NumberFieldItem, onValueChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedTextField(
            value = item.value,
            onValueChange = onValueChange,
            placeholder = { Text(item.placeholder) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
    }
}

@Composable
private fun DropdownItem(item: ChecklistItem.DropdownItem, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = item.selectedOption ?: "Select option",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, "Drop down") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            // Note: In a real implementation, we'd put a clickable Surface or Box over the
            // TextField
            // to handle the click properly without focusing the text field.
            Surface(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { expanded = true },
                color = Color.Transparent
            ) {}

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                item.options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MultiSelectItem(
    item: ChecklistItem.MultiSelectItem,
    onOptionSelected: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Surface(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(8.dp)
                    )
                    .clickable { /* Show multi-select dialog */ },
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text =
                        if (item.selectedOptions.isEmpty()) "Select parts..."
                        else item.selectedOptions.joinToString(", "),
                    color =
                        if (item.selectedOptions.isEmpty())
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else MaterialTheme.colorScheme.onSurface
                )
                Icon(Icons.Default.ChevronRight, "Select")
            }
        }
    }
}

@Composable
private fun PhotoItem(item: ChecklistItem.PhotoItem, onPhotoAdded: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item.photoUrls.forEach { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Photo",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            if (item.photoUrls.size < item.maxPhotos) {
                Surface(
                    modifier =
                        Modifier
                            .size(80.dp)
                            .clickable {
                                onPhotoAdded("new_photo_url")
                            }, // Placeholder action
                    shape = RoundedCornerShape(8.dp),
                    border =
                        androidx.compose.foundation.BorderStroke(
                            2.dp,
                            MaterialTheme.colorScheme.outlineVariant
                        ),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = "Add photo",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Add",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NoteItem(item: ChecklistItem.NoteItem, onValueChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedTextField(
            value = item.value,
            onValueChange = onValueChange,
            placeholder = { Text(item.placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(8.dp),
            minLines = 3
        )
    }
}
