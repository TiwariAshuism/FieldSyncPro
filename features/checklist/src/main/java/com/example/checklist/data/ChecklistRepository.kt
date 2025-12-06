package com.example.checklist.data

import kotlinx.coroutines.delay

class ChecklistRepository {

    suspend fun getChecklist(jobId: String): Result<Checklist> {
        delay(500) // Simulate network delay

        // Mock data matching the HTML design
        val safetySection =
            ChecklistSection(
                id = "sec_safety",
                title = "Safety Inspection",
                items =
                    listOf(
                        ChecklistItem.CheckboxItem(
                            id = "item_fluid",
                            label = "Check fluid levels",
                            required = true
                        ),
                        ChecklistItem.CheckboxItem(
                            id = "item_tire",
                            label = "Inspect tire pressure",
                            required = true
                        )
                    )
            )

        val unitDetailsSection =
            ChecklistSection(
                id = "sec_unit",
                title = "Unit Details",
                items =
                    listOf(
                        ChecklistItem.TextFieldItem(
                            id = "item_serial",
                            label = "Serial Number",
                            placeholder = "e.g., SN123456789",
                            required = true
                        ),
                        ChecklistItem.NumberFieldItem(
                            id = "item_voltage",
                            label = "Voltage Reading",
                            placeholder = "Enter voltage",
                            required = true
                        ),
                        ChecklistItem.DropdownItem(
                            id = "item_condition",
                            label = "Unit Condition",
                            options =
                                listOf(
                                    "Good",
                                    "Fair",
                                    "Poor",
                                    "Needs Replacement"
                                ),
                            required = true
                        ),
                        ChecklistItem.MultiSelectItem(
                            id = "item_parts",
                            label = "Parts Replaced",
                            options =
                                listOf(
                                    "Filter",
                                    "Belt",
                                    "Compressor",
                                    "Fan Motor",
                                    "Capacitor"
                                )
                        ),
                        ChecklistItem.PhotoItem(
                            id = "item_photos",
                            label = "Photos of Unit",
                            photoUrls =
                                listOf(
                                    "https://lh3.googleusercontent.com/aida-public/AB6AXuD9ycL4yEPZqKTG4b1yL1YR7W3Gt1gnQfEg5oPizjx9ArWzUBuAJVbxXWANNtv62OKDOMZK_63IEE0JS9CXlK5HzsXs0Hxzgu9EehRLAqH3oNUV04y8VJvdzxiz6A91HjLH21nhbTef4dZ-IEwEYqwDmcLzKYmreOnK_JWVfYF5VnPSdFPhzFVmrOqO_AzVZ-9mjIcPHZj6OXVEfiRFkn6_Km0un4VgbWw7y5MLNzYmoozTPMc4zQpKlAIZ1zJG_qSArSEbqP1vTg",
                                    "https://lh3.googleusercontent.com/aida-public/AB6AXuB5W8V6_aXjx-cpj_RXVU9yJWP_RO6yqcG5XpEMNwyRppaKb4n--AJcWtZbedGF3OgAknrfqcWShDYMvSRtfDRW-VMOD3SJ6VrFZq8ZeWZ02f2IIAWjSQYRQdND4b1o8Vwl3MEaHW0CUkodk6--Jt9S230qxKLF2ZZX3QpBXSqMIbcFO3dknCDA5YdZIgzKkOEW7ZPQ-yAOyhkNMlcmESnmlHNzHoakLlFgFzAu_EzDzNXn3Sg95ab5wiLvYa_beiuC1c1qzf4a3Q"
                                )
                        ),
                        ChecklistItem.NoteItem(
                            id = "item_notes",
                            label = "Technician Notes",
                            placeholder = "Add any additional notes here..."
                        )
                    )
            )

        val checklist =
            Checklist(
                id = "chk_001",
                jobId = jobId,
                title = "HVAC Unit Inspection",
                description = "Standard inspection procedure for HVAC units",
                sections = listOf(safetySection, unitDetailsSection)
            )

        return Result.success(checklist)
    }

    suspend fun submitChecklist(checklist: Checklist): Result<Unit> {
        delay(1000) // Simulate network delay
        return Result.success(Unit)
    }
}
