package com.example.checklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checklist.data.Checklist
import com.example.checklist.data.ChecklistItem
import com.example.checklist.domain.SubmitChecklistUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChecklistViewModel(private val submitChecklistUseCase: SubmitChecklistUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<ChecklistUiState>(ChecklistUiState.Loading)
    val uiState: StateFlow<ChecklistUiState> = _uiState.asStateFlow()

    fun loadChecklist(jobId: String) {
        viewModelScope.launch {
            _uiState.value = ChecklistUiState.Loading
            val result = submitChecklistUseCase.getChecklist(jobId)

            if (result.isSuccess) {
                _uiState.value = ChecklistUiState.Success(result.getOrThrow())
            } else {
                _uiState.value =
                    ChecklistUiState.Error(
                        result.exceptionOrNull()?.message ?: "Failed to load checklist"
                    )
            }
        }
    }

    fun onAnswerChanged(itemId: String, newValue: String) {
        val currentState = _uiState.value
        if (currentState is ChecklistUiState.Success) {
            val updatedChecklist = updateChecklistValue(currentState.checklist, itemId, newValue)
            _uiState.value = ChecklistUiState.Success(updatedChecklist)
        }
    }

    private fun updateChecklistValue(
        checklist: Checklist,
        itemId: String,
        newValue: String
    ): Checklist {
        val updatedSections =
            checklist.sections.map { section ->
                val updatedItems =
                    section.items.map { item ->
                        if (item.id == itemId) {
                            when (item) {
                                is ChecklistItem.CheckboxItem ->
                                    item.copy(checked = newValue.toBoolean())

                                is ChecklistItem.TextFieldItem ->
                                    item.copy(value = newValue)

                                is ChecklistItem.NumberFieldItem ->
                                    item.copy(value = newValue)

                                is ChecklistItem.DropdownItem ->
                                    item.copy(selectedOption = newValue)

                                is ChecklistItem.NoteItem -> item.copy(value = newValue)
                                is ChecklistItem.MultiSelectItem ->
                                    item // Handle multi-select separately if needed
                                is ChecklistItem.PhotoItem ->
                                    item // Handle photos separately
                            }
                        } else {
                            item
                        }
                    }
                section.copy(items = updatedItems)
            }
        return checklist.copy(sections = updatedSections)
    }

    fun onPhotoAdded(itemId: String, photoUrl: String) {
        val currentState = _uiState.value
        if (currentState is ChecklistUiState.Success) {
            val updatedChecklist = updateChecklistPhoto(currentState.checklist, itemId, photoUrl)
            _uiState.value = ChecklistUiState.Success(updatedChecklist)
        }
    }

    private fun updateChecklistPhoto(
        checklist: Checklist,
        itemId: String,
        photoUrl: String
    ): Checklist {
        val updatedSections =
            checklist.sections.map { section ->
                val updatedItems =
                    section.items.map { item ->
                        if (item.id == itemId && item is ChecklistItem.PhotoItem) {
                            item.copy(photoUrls = item.photoUrls + photoUrl)
                        } else {
                            item
                        }
                    }
                section.copy(items = updatedItems)
            }
        return checklist.copy(sections = updatedSections)
    }
}

sealed class ChecklistUiState {
    data object Loading : ChecklistUiState()
    data class Success(val checklist: Checklist) : ChecklistUiState()
    data class Error(val message: String) : ChecklistUiState()
}
