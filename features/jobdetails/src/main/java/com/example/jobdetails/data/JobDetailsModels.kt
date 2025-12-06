package com.example.jobdetails.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/** Represents the complete details of a field service job */
data class JobDetails(
    val jobId: String,
    val customer: String,
    val location: String,
    val scheduledTime: LocalDateTime,
    val mapImageUrl: String,
    val syncStatus: SyncStatus,
    val lastSyncedMinutesAgo: Int
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedScheduledTime(): String {
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy, hh:mm a")
        return scheduledTime.format(formatter)
    }

    fun getLastSyncedText(): String {
        return when {
            lastSyncedMinutesAgo < 1 -> "Last synced: just now"
            lastSyncedMinutesAgo == 1 -> "Last synced: 1 minute ago"
            lastSyncedMinutesAgo < 60 -> "Last synced: $lastSyncedMinutesAgo minutes ago"
            else -> {
                val hours = lastSyncedMinutesAgo / 60
                if (hours == 1) "Last synced: 1 hour ago" else "Last synced: $hours hours ago"
            }
        }
    }
}

/** Represents a serviceable asset associated with a job */
data class ServiceableAsset(
    val assetId: String,
    val name: String,
    val type: AssetType,
    val iconName: String
)

/** Types of serviceable assets */
enum class AssetType {
    HVAC,
    VENTILATION,
    ELECTRICAL,
    PLUMBING,
    MECHANICAL,
    OTHER
}

/** Represents different tabs in the job details screen */
enum class JobTab(val title: String) {
    OVERVIEW("Overview"),
    CHECKLIST("Checklist"),
    ASSETS("Assets"),
    PHOTOS("Photos"),
    NOTES("Notes")
}

/** Represents the synchronization status of the job data */
sealed class SyncStatus {
    data object Online : SyncStatus()
    data object Offline : SyncStatus()
    data class Syncing(val progress: Float) : SyncStatus()
    data class Error(val message: String) : SyncStatus()

    fun isOffline(): Boolean = this is Offline
}
