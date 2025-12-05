package com.example.jobdetails.navigation

object JobDetailsRoute {
    const val JOB_DETAILS = "job/{jobId}"

    fun createRoute(jobId: String) = "job/$jobId"
}
