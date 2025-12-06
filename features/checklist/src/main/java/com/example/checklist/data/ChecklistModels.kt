package com.example.checklist.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

/** Represents a complete checklist for a job */
data class Checklist @RequiresApi(Build.VERSION_CODES.O) constructor(
    val id: String,
    val jobId: String,
    val title: String,
    val description: String,
    val sections: List<ChecklistSection>,
    val status: ChecklistStatus = ChecklistStatus.DRAFT,
    val lastUpdated: LocalDateTime = LocalDateTime.now()
)

/** Represents a section within a checklist */
data class ChecklistSection(val id: String, val title: String, val items: List<ChecklistItem>)

/** Represents the status of a checklist */
enum class ChecklistStatus {
    DRAFT,
    COMPLETED,
    SYNCED
}

/** Sealed class representing different types of checklist items */
sealed class ChecklistItem {
    abstract val id: String
    abstract val label: String
    abstract val required: Boolean

    data class CheckboxItem(
        override val id: String,
        override val label: String,
        override val required: Boolean = false,
        val checked: Boolean = false
    ) : ChecklistItem()

    data class TextFieldItem(
        override val id: String,
        override val label: String,
        override val required: Boolean = false,
        val value: String = "",
        val placeholder: String = ""
    ) : ChecklistItem()

    data class NumberFieldItem(
        override val id: String,
        override val label: String,
        override val required: Boolean = false,
        val value: String = "",
        val placeholder: String = ""
    ) : ChecklistItem()

    data class DropdownItem(
        override val id: String,
        override val label: String,
        override val required: Boolean = false,
        val options: List<String>,
        val selectedOption: String? = null
    ) : ChecklistItem()

    data class MultiSelectItem(
        override val id: String,
        override val label: String,
        override val required: Boolean = false,
        val options: List<String>,
        val selectedOptions: List<String> = emptyList()
    ) : ChecklistItem()

    data class PhotoItem(
        override val id: String,
        override val label: String,
        override val required: Boolean = false,
        val photoUrls: List<String> = emptyList(),
        val maxPhotos: Int = 5
    ) : ChecklistItem()

    data class NoteItem(
        override val id: String,
        override val label: String,
        override val required: Boolean = false,
        val value: String = "",
        val placeholder: String = ""
    ) : ChecklistItem()
}
