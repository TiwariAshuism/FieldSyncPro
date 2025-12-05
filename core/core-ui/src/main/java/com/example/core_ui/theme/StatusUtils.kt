package com.example.core_ui.theme

// Helper function for job status colors
enum class JobStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}

fun getStatusColorForJob(status: JobStatus): StatusColor {
    return when (status) {
        JobStatus.PENDING ->
                StatusColor(
                        label = "PENDING",
                        textColor = StatusPending,
                        backgroundColor = StatusPending.copy(alpha = 0.2f)
                )
        JobStatus.IN_PROGRESS ->
                StatusColor(
                        label = "IN PROGRESS",
                        textColor = StatusInProgress,
                        backgroundColor = StatusInProgress.copy(alpha = 0.2f)
                )
        JobStatus.COMPLETED ->
                StatusColor(
                        label = "COMPLETED",
                        textColor = StatusCompleted,
                        backgroundColor = StatusCompleted.copy(alpha = 0.2f)
                )
    }
}
