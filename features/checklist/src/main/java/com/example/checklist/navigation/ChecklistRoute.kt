package com.example.checklist.navigation

object ChecklistRoute {
    const val CHECKLIST = "checklist/{jobId}"

    fun createRoute(jobId: String) = "checklist/$jobId"
}
