package com.example.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(onSuccess: () -> Unit, viewModel: AuthViewModel = viewModel()) {
    val formState by viewModel.formState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    // Handle success state
    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            onSuccess()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(
                modifier =
                        Modifier.fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo Section
            Spacer(modifier = Modifier.height(80.dp))

            Box(
                    modifier =
                            Modifier.size(64.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    ),
                    contentAlignment = Alignment.Center
            ) {
                Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = "App Logo",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Segmented Control (Login/Register Toggle)
            SegmentedControl(
                    isLoginMode = formState.isLoginMode,
                    onModeChange = { viewModel.toggleAuthMode() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Email Field
            OutlinedTextField(
                    value = formState.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Email")
                    },
                    isError = formState.emailError != null,
                    supportingText = formState.emailError?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                    value = formState.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "Password")
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                            Icon(
                                    imageVector =
                                            if (formState.isPasswordVisible)
                                                    Icons.Default.Visibility
                                            else Icons.Default.VisibilityOff,
                                    contentDescription =
                                            if (formState.isPasswordVisible) "Hide password"
                                            else "Show password"
                            )
                        }
                    },
                    visualTransformation =
                            if (formState.isPasswordVisible) VisualTransformation.None
                            else PasswordVisualTransformation(),
                    isError = formState.passwordError != null,
                    supportingText = formState.passwordError?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    singleLine = true
            )

            // Forgot Password Link (Only in Login mode)
            if (formState.isLoginMode) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                            text = "Forgot Password?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { /* TODO: Handle forgot password */}
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Login/Register Button
            Button(
                    onClick = { viewModel.onSubmit() },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = MaterialTheme.shapes.large,
                    enabled = uiState !is AuthUiState.Loading
            ) {
                if (uiState is AuthUiState.Loading) {
                    CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                            text = if (formState.isLoginMode) "Login" else "Register",
                            style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Divider with "Or continue with"
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text(
                        text = "Or continue with",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Social Login Buttons
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                // Google Button
                SocialLoginButton(
                        icon = Icons.Default.Email, // Placeholder for Google
                        contentDescription = "Login with Google",
                        onClick = { /* TODO: Implement Google login */}
                )

                Spacer(modifier = Modifier.width(24.dp))

                // Apple Button
                SocialLoginButton(
                        icon = Icons.Default.FrontHand, // Using Apple icon
                        contentDescription = "Login with Apple",
                        onClick = { /* TODO: Implement Apple login */}
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun SegmentedControl(isLoginMode: Boolean, onModeChange: () -> Unit) {
    Row(
            modifier =
                    Modifier.fillMaxWidth()
                            .height(48.dp)
                            .clip(MaterialTheme.shapes.large)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                            .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                    shape = MaterialTheme.shapes.large
                            )
                            .padding(4.dp)
    ) {
        // Login Button
        SegmentButton(
                text = "Login",
                isSelected = isLoginMode,
                onClick = { if (!isLoginMode) onModeChange() },
                modifier = Modifier.weight(1f)
        )

        // Register Button
        SegmentButton(
                text = "Register",
                isSelected = !isLoginMode,
                onClick = { if (isLoginMode) onModeChange() },
                modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SegmentButton(
        text: String,
        isSelected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    Box(
            modifier =
                    modifier.fillMaxHeight()
                            .clip(MaterialTheme.shapes.medium)
                            .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else Color.Transparent
                            )
                            .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
    ) {
        Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color =
                        if (isSelected) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SocialLoginButton(
        icon: androidx.compose.ui.graphics.vector.ImageVector,
        contentDescription: String,
        onClick: () -> Unit
) {
    Box(
            modifier =
                    Modifier.size(56.dp)
                            .clip(CircleShape)
                            .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    shape = CircleShape
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
    ) {
        Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
        )
    }
}
