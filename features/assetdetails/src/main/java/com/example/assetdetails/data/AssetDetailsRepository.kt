package com.example.assetdetails.data

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.delay
import java.time.LocalDate

class AssetDetailsRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAssetDetails(assetId: String): Result<AssetDetails> {
        delay(500) // Simulate network delay

        // Mock data matching the HTML design
        val inspectionHistory =
            listOf(
                InspectionRecord(
                    id = "ins_001",
                    date = LocalDate.of(2023, 10, 25),
                    status = InspectionStatus.PASSED,
                    summary = "Inspection Passed",
                    performedBy = "J. Doe"
                ),
                InspectionRecord(
                    id = "ins_002",
                    date = LocalDate.of(2023, 7, 14),
                    status = InspectionStatus.WARNING,
                    summary = "Filter Replacement Recommended",
                    performedBy = "S. Smith"
                ),
                InspectionRecord(
                    id = "ins_003",
                    date = LocalDate.of(2023, 4, 2),
                    status = InspectionStatus.FAILED,
                    summary = "Inspection Failed - Seal Broken",
                    performedBy = "M. Jones"
                )
            )

        val assetDetails =
            AssetDetails(
                id = assetId,
                name = "XJ-45 Industrial Pump",
                model = "XJ-45 Industrial Pump",
                serialNumber = "SN-A9872B-01",
                lastInspectionDate = LocalDate.of(2023, 10, 25),
                imageUrl =
                    "https://lh3.googleusercontent.com/aida-public/AB6AXuC18FZPrmyq0KGLNy9tmbm6kfP0BYVGlftQbG7e8shy0v2FlpaUSxvOeSXp7yF1Mk3nYpN4B0682rOUXwbZlLUMVbB7y1a6V7PjQOZJItieAisnU-L16k-kMPg9L0v8CrMYqPL28KcrmU8w8GE79Xrqa2UHimvDsHL96lmJ5KknqrG0aLxA8ej1ImliiGNC76P56vjnfrOYmG8slIR5xZ2gQFI7VuAKTAwaNxD4NX_daf7_UD57H1WuMNSFh-IO6sBje0D02D248A",
                inspectionHistory = inspectionHistory
            )

        return Result.success(assetDetails)
    }
}
