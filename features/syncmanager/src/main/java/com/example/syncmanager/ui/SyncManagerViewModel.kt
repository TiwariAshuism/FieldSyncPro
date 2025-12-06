package com.example.syncmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncmanager.data.SyncItem
import com.example.syncmanager.data.SyncRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SyncManagerViewModel(
        private val repository: SyncRepository = SyncRepository() // Manual DI for now
) : ViewModel() {

    val syncItems: StateFlow<List<SyncItem>> =
            repository.syncItems.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = emptyList()
            )

    fun onSyncNow() {
        viewModelScope.launch { repository.syncNow() }
    }

    fun onRetryItem(itemId: String) {
        viewModelScope.launch { repository.retryItem(itemId) }
    }
}
