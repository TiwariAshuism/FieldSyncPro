package com.example.core_ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.core_ui.R

val Inter = FontFamily(
    Font(R.font.inter_regular, weight = FontWeight.Normal),
    Font(R.font.inter_medium, weight = FontWeight.Medium),
    Font(R.font.inter_bold, weight = FontWeight.Bold),
    Font(R.font.inter_extrabold, weight = FontWeight.ExtraBold)
)

val AppTypography = Typography(
    bodyLarge = Typography().bodyLarge.copy(fontFamily = Inter),
    bodyMedium = Typography().bodyMedium.copy(fontFamily = Inter),
    titleLarge = Typography().titleLarge.copy(fontFamily = Inter),
    titleMedium = Typography().titleMedium.copy(fontFamily = Inter),
)
