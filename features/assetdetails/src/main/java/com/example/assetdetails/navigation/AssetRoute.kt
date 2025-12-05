package com.example.assetdetails.navigation

object AssetRoute {
    const val ASSET_DETAILS = "asset/{assetId}"

    fun createRoute(assetId: String) = "asset/$assetId"
}
