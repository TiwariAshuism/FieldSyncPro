package com.example.assetdetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assetdetails.data.AssetDetails
import com.example.assetdetails.domain.GetAssetDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AssetDetailsViewModel(private val getAssetDetailsUseCase: GetAssetDetailsUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<AssetDetailsUiState>(AssetDetailsUiState.Loading)
    val uiState: StateFlow<AssetDetailsUiState> = _uiState.asStateFlow()

    fun loadAssetDetails(assetId: String) {
        viewModelScope.launch {
            _uiState.value = AssetDetailsUiState.Loading
            val result = getAssetDetailsUseCase(assetId)

            if (result.isSuccess) {
                _uiState.value = AssetDetailsUiState.Success(result.getOrThrow())
            } else {
                _uiState.value =
                    AssetDetailsUiState.Error(
                        result.exceptionOrNull()?.message ?: "Failed to load asset details"
                    )
            }
        }
    }
}

sealed class AssetDetailsUiState {
    data object Loading : AssetDetailsUiState()
    data class Success(val assetDetails: AssetDetails) : AssetDetailsUiState()
    data class Error(val message: String) : AssetDetailsUiState()
}
