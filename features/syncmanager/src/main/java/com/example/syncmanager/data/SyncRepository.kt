package com.example.syncmanager.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SyncRepository {

    private val _syncItems = MutableStateFlow<List<SyncItem>>(emptyList())
    val syncItems: StateFlow<List<SyncItem>> = _syncItems.asStateFlow()

    init {
        // Initialize with mock data matching the HTML design
        _syncItems.value =
                listOf(
                        SyncItem(
                                id = "1",
                                type = SyncType.Forms,
                                status = SyncStatus.Failed,
                                errorMessage = "Network Error"
                        ),
                        SyncItem(
                                id = "2",
                                type = SyncType.Checklists,
                                status = SyncStatus.Synced,
                                lastSynced = "10:30 AM"
                        ),
                        SyncItem(id = "3", type = SyncType.Images, status = SyncStatus.Syncing),
                        SyncItem(id = "4", type = SyncType.Notes, status = SyncStatus.Pending)
                )
    }

    suspend fun syncNow() {
        // Simulate sync process
        _syncItems.update { items ->
            items.map { it.copy(status = SyncStatus.Syncing, errorMessage = null) }
        }

        delay(2000) // Simulate network delay

        _syncItems.update { items ->
            items.map { it.copy(status = SyncStatus.Synced, lastSynced = "Just now") }
        }
    }

    suspend fun retryItem(itemId: String) {
        _syncItems.update { items ->
            items.map {
                if (it.id == itemId) {
                    it.copy(status = SyncStatus.Syncing, errorMessage = null)
                } else {
                    it
                }
            }
        }

        delay(1500)

        _syncItems.update { items ->
            items.map {
                if (it.id == itemId) {
                    it.copy(status = SyncStatus.Synced, lastSynced = "Just now")
                } else {
                    it
                }
            }
        }
    }
}
