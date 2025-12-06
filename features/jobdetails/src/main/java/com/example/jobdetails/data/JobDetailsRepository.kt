package com.example.jobdetails.data

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.delay
import java.time.LocalDateTime

/**
 * Repository for job details and related data In production, this would interact with local
 * database and remote API
 */
class JobDetailsRepository {

    // Simulated network delay
    private val networkDelayMs = 500L

    /** Fetches job details for a specific job ID Returns Result with JobDetails or error */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getJobDetails(jobId: String): Result<JobDetails> {
        return try {
            delay(networkDelayMs) // Simulate network call

            // Sample data - in production, this would come from API/database
            val jobDetails =
                JobDetails(
                    jobId = jobId,
                    customer = "ACME Corp",
                    location = "123 Industrial Way, Anytown, USA",
                    scheduledTime = LocalDateTime.of(2023, 10, 26, 10, 0),
                    mapImageUrl =
                        "https://lh3.googleusercontent.com/aida-public/AB6AXuAZAQYbZSJFrocpsHym9k2EpUVUh_6tzon8-7J_BzaXXsZPOQcjdUChk8Wpcs2E_Hwpv6dteYXfO_SGOKXGn3aqRWX_NNHutos5sWDhp-0K-BTYuKbcpMBgsyCOPlxA3O3LCLcUxg0ali1fqEAyqRTnbRh3YTn1nGGZBfPCuZHoNOdMKg068sW4Sp3FE2ipaFJIdzreKZLnDCndivuUcqWdSKSI_x8sMvynohLEu_c1LFIY5ZTLTE7lGuWf1dwBcuxG9W_X-gywVg",
                    syncStatus = SyncStatus.Offline,
                    lastSyncedMinutesAgo = 2
                )

            Result.success(jobDetails)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetches serviceable assets for a specific job Returns Result with list of ServiceableAsset or
     * error
     */
    suspend fun getServiceableAssets(jobId: String): Result<List<ServiceableAsset>> {
        return try {
            delay(networkDelayMs) // Simulate network call

            // Sample data - in production, this would come from API/database
            val assets =
                listOf(
                    ServiceableAsset(
                        assetId = "HVAC-001",
                        name = "HVAC Unit - Roof A",
                        type = AssetType.HVAC,
                        iconName = "ac_unit"
                    ),
                    ServiceableAsset(
                        assetId = "VENT-004",
                        name = "Ventilation Fan - Sector B",
                        type = AssetType.VENTILATION,
                        iconName = "wind_power"
                    ),
                    ServiceableAsset(
                        assetId = "PWR-001",
                        name = "Main Power Grid Connector",
                        type = AssetType.ELECTRICAL,
                        iconName = "electrical_services"
                    )
                )

            Result.success(assets)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Checks if the device is currently offline In production, this would check actual network
     * connectivity
     */
    fun isOffline(): Boolean {
        // For demo purposes, returning true to show offline banner
        return true
    }
}
