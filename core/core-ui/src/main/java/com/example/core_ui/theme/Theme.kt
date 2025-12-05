package com.example.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors =
        lightColorScheme(
                primary = Primary,
                background = BackgroundLight,
                surface = CardLight,
                onPrimary = Color.White,
                onBackground = TextPrimaryLight,
                onSurface = TextPrimaryLight,
                surfaceVariant = InputBackgroundLight,
                onSurfaceVariant = TextSecondaryLight
        )

private val DarkColors =
        darkColorScheme(
                primary = Primary,
                background = BackgroundDark,
                surface = CardDark,
                onPrimary = Color.White,
                onBackground = TextPrimaryDark,
                onSurface = TextPrimaryDark,
                surfaceVariant = InputBackgroundDark,
                onSurfaceVariant = TextSecondaryDark
        )

@Composable
fun FieldForceTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            typography = AppTypography,
            shapes = AppShapes,
            content = content
    )
}
