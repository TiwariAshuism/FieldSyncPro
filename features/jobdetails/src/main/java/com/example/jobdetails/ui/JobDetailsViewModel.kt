package com.example.jobdetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobdetails.data.JobDetails
import com.example.jobdetails.data.JobTab
import com.example.jobdetails.data.ServiceableAsset
import com.example.jobdetails.domain.GetJobDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** ViewModel for Job Details screen Manages UI state and handles business logic */
class JobDetailsViewModel(private val getJobDetailsUseCase: GetJobDetailsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<JobDetailsUiState>(JobDetailsUiState.Loading)
    val uiState: StateFlow<JobDetailsUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow(JobTab.OVERVIEW)
    val selectedTab: StateFlow<JobTab> = _selectedTab.asStateFlow()

    private val _isOffline = MutableStateFlow(false)
    val isOffline: StateFlow<Boolean> = _isOffline.asStateFlow()

    /** Loads job details and assets for the specified job ID */
    fun loadJobDetails(jobId: String) {
        viewModelScope.launch {
            _uiState.value = JobDetailsUiState.Loading
            _isOffline.value = getJobDetailsUseCase.isOffline()

            try {
                // Fetch job details
                val jobDetailsResult = getJobDetailsUseCase.getJobDetails(jobId)

                if (jobDetailsResult.isFailure) {
                    _uiState.value =
                        JobDetailsUiState.Error(
                            jobDetailsResult.exceptionOrNull()?.message
                                ?: "Failed to load job details"
                        )
                    return@launch
                }

                // Fetch serviceable assets
                val assetsResult = getJobDetailsUseCase.getServiceableAssets(jobId)

                if (assetsResult.isFailure) {
                    _uiState.value =
                        JobDetailsUiState.Error(
                            assetsResult.exceptionOrNull()?.message
                                ?: "Failed to load assets"
                        )
                    return@launch
                }

                // Success - update UI state with data
                _uiState.value =
                    JobDetailsUiState.Success(
                        jobDetails = jobDetailsResult.getOrThrow(),
                        assets = assetsResult.getOrThrow()
                    )
            } catch (e: Exception) {
                _uiState.value =
                    JobDetailsUiState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    /** Updates the selected tab */
    fun onTabSelected(tab: JobTab) {
        _selectedTab.value = tab
    }

    /** Handles Get Directions button click */
    fun onGetDirectionsClick(location: String) {
        // In production, this would open maps app with the location
        // For now, this is a placeholder for the action
    }

    /** Handles Start Inspection button click */
    fun onStartInspectionClick(jobId: String) {
        // In production, this would navigate to inspection screen
        // For now, this is a placeholder for the action
    }

    /** Handles asset item click */
    fun onAssetClick(assetId: String) {
        // In production, this would navigate to asset details screen
        // For now, this is a placeholder for the action
    }
}

/** Sealed class representing different UI states */
sealed class JobDetailsUiState {
    data object Loading : JobDetailsUiState()

    data class Success(val jobDetails: JobDetails, val assets: List<ServiceableAsset>) :
        JobDetailsUiState()

    data class Error(val message: String) : JobDetailsUiState()
}
