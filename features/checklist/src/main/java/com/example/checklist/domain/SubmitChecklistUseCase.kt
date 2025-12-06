package com.example.checklist.domain

import com.example.checklist.data.Checklist
import com.example.checklist.data.ChecklistRepository

class SubmitChecklistUseCase(private val repository: ChecklistRepository) {

    suspend fun getChecklist(jobId: String): Result<Checklist> {
        return repository.getChecklist(jobId)
    }

    suspend fun submitChecklist(checklist: Checklist): Result<Unit> {
        // Basic validation
        if (checklist.sections.isEmpty()) {
            return Result.failure(IllegalArgumentException("Checklist cannot be empty"))
        }

        return repository.submitChecklist(checklist)
    }
}
