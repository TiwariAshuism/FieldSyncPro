package com.example.assetdetails.domain

import com.example.assetdetails.data.AssetDetails
import com.example.assetdetails.data.AssetDetailsRepository

class GetAssetDetailsUseCase(private val repository: AssetDetailsRepository) {
    suspend operator fun invoke(assetId: String): Result<AssetDetails> {
        return repository.getAssetDetails(assetId)
    }
}
