package com.example.core_ui.theme

import androidx.compose.ui.graphics.Color

// Primary color from design
val Primary = Color(0xFF137FEC)

// Background colors
val BackgroundLight = Color(0xFFF6F7F8)
val BackgroundDark = Color(0xFF101922)

// Surface/Card colors
val CardLight = Color(0xFFFFFFFF)
val CardDark = Color(0xFF1C1C1E)

// Input field backgrounds
val InputBackgroundLight = Color(0xFFF6F7F8)
val InputBackgroundDark = Color(0xFF101922)

// Text colors
val TextPrimaryLight = Color(0xFF1C1C1E)
val TextPrimaryDark = Color(0xFFFFFFFF)

val TextSecondaryLight = Color(0xFF8A8A8E)
val TextSecondaryDark = Color(0xFF8E8E93)

// Status colors
val StatusPending = Color(0xFF8A8A8E)
val StatusInProgress = Color(0xFFFF9500)
val StatusCompleted = Color(0xFF34C759)

// Status color data class
data class StatusColor(val label: String, val textColor: Color, val backgroundColor: Color)
