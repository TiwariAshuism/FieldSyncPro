package com.example.assetdetails.data

import java.time.LocalDate

/** Represents the details of an asset */
data class AssetDetails(
    val id: String,
    val name: String,
    val model: String,
    val serialNumber: String,
    val lastInspectionDate: LocalDate,
    val imageUrl: String,
    val inspectionHistory: List<InspectionRecord>
)

/** Represents a single inspection record in the history */
data class InspectionRecord(
    val id: String,
    val date: LocalDate,
    val status: InspectionStatus,
    val summary: String,
    val performedBy: String
)

/** Status of an inspection */
enum class InspectionStatus {
    PASSED,
    WARNING,
    FAILED
}
