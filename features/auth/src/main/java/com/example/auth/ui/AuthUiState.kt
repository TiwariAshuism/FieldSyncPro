package com.example.auth.ui

data class AuthFormState(
    val email: String = "",
    val password: String = "",
    val isLoginMode: Boolean = true,
    val isPasswordVisible: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
)

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val message: String) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
