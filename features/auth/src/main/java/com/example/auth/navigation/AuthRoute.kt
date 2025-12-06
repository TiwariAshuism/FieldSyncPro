package com.example.auth.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.auth.ui.LoginScreen


object AuthRoute {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
}


fun NavGraphBuilder.authGraph(
    onLoginSuccess: () -> Unit
) {
    composable(AuthRoute.LOGIN) {
        LoginScreen(onSuccess = onLoginSuccess)
    }
}
