package com.example.auth.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel : ViewModel() {

    private val _formState = MutableStateFlow(AuthFormState())
    val formState: StateFlow<AuthFormState> = _formState.asStateFlow()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _formState.update { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(password: String) {
        _formState.update { it.copy(password = password, passwordError = null) }
    }

    fun togglePasswordVisibility() {
        _formState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun toggleAuthMode() {
        _formState.update {
            it.copy(isLoginMode = !it.isLoginMode, emailError = null, passwordError = null)
        }
    }

    fun validateEmail(email: String): Boolean {
        return when {
            email.isBlank() -> {
                _formState.update { it.copy(emailError = "Email is required") }
                false
            }

            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _formState.update { it.copy(emailError = "Invalid email format") }
                false
            }

            else -> true
        }
    }

    fun validatePassword(password: String): Boolean {
        return when {
            password.isBlank() -> {
                _formState.update { it.copy(passwordError = "Password is required") }
                false
            }

            password.length < 6 -> {
                _formState.update {
                    it.copy(passwordError = "Password must be at least 6 characters")
                }
                false
            }

            else -> true
        }
    }

    fun onSubmit() {
        val currentState = _formState.value
        val isEmailValid = validateEmail(currentState.email)
        val isPasswordValid = validatePassword(currentState.password)

        if (isEmailValid && isPasswordValid) {
            _uiState.value = AuthUiState.Loading
            // TODO: Implement actual authentication logic
            // For now, simulate success
            _uiState.value =
                AuthUiState.Success(
                    if (currentState.isLoginMode) "Login successful"
                    else "Registration successful"
                )
        }
    }
}
