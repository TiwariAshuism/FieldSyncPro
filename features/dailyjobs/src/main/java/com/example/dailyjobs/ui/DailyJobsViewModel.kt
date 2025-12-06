package com.example.dailyjobs.ui

import androidx.lifecycle.ViewModel
import com.example.core_ui.theme.JobStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DailyJobsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<DailyJobsUiState>(DailyJobsUiState.Loading)
    val uiState: StateFlow<DailyJobsUiState> = _uiState.asStateFlow()

    init {
        loadJobs()
    }

    private fun loadJobs() {
        // TODO: Load from repository
        // For now, using sample data
        val sampleJobs =
            listOf(
                Job(
                    id = "1",
                    title = "HVAC Unit #3 Inspection",
                    location = "Main Campus, Building A, Floor 3, Room 301",
                    status = JobStatus.PENDING,
                    isSynced = true
                ),
                Job(
                    id = "2",
                    title = "Fire Extinguisher Check",
                    location = "Main Campus, Building B, Floor 1, Room 105",
                    status = JobStatus.IN_PROGRESS,
                    isSynced = false
                ),
                Job(
                    id = "3",
                    title = "Plumbing Leak Repair",
                    location = "West Wing, Building C, Basement, Utility Closet",
                    status = JobStatus.COMPLETED,
                    isSynced = true
                )
            )

        _uiState.value = DailyJobsUiState.Success(jobs = sampleJobs, isOnline = true)
    }

    fun onJobClick(jobId: String) {
        // Handle job click - navigate to details
    }

    fun onStartJob(jobId: String) {
        // Handle start job action
    }
}
