package com.example.jobdetails.domain

import com.example.jobdetails.data.JobDetails
import com.example.jobdetails.data.JobDetailsRepository
import com.example.jobdetails.data.ServiceableAsset

/**
 * Use case for fetching job details and related data Encapsulates business logic for job details
 * screen
 */
class GetJobDetailsUseCase(private val repository: JobDetailsRepository) {
    /** Fetches job details for the specified job ID */
    suspend fun getJobDetails(jobId: String): Result<JobDetails> {
        return repository.getJobDetails(jobId)
    }

    /** Fetches serviceable assets for the specified job ID */
    suspend fun getServiceableAssets(jobId: String): Result<List<ServiceableAsset>> {
        return repository.getServiceableAssets(jobId)
    }

    /** Checks if the device is currently in offline mode */
    fun isOffline(): Boolean {
        return repository.isOffline()
    }
}
