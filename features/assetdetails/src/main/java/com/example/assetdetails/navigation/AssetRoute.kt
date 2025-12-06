package com.example.assetdetails.navigation

object AssetRoute {
    const val ASSET_DETAILS = "asset/{assetId}"

    fun createRoute(assetId: String) = "asset/$assetId"

    const val PHOTO_CAPTURE = "asset_details/{assetId}/photo"

    fun assetDetails(assetId: String) = "asset_details/$assetId"
    fun photoCapture(assetId: String) = "asset_details/$assetId/photo"
}
