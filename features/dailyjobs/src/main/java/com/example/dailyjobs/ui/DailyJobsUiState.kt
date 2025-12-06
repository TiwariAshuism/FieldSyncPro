package com.example.dailyjobs.ui

import com.example.core_ui.theme.JobStatus

data class Job(
    val id: String,
    val title: String,
    val location: String,
    val status: JobStatus,
    val isSynced: Boolean = true
)

sealed class DailyJobsUiState {
    object Loading : DailyJobsUiState()
    data class Success(val jobs: List<Job>, val isOnline: Boolean = true) : DailyJobsUiState()
    data class Error(val message: String) : DailyJobsUiState()
}
