package com.ndq.tododo.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val fontFamily = getFontFamily()

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = fontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = fontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = fontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = fontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = fontFamily),
    headlineSmall = baseline.headlineSmall.copy(
        fontSize = 40.sp,
        lineHeight = 48.sp,
        fontWeight = FontWeight.W600,
        fontFamily = fontFamily,
    ),
    titleLarge = baseline.titleLarge.copy(fontFamily = fontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = fontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = fontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = fontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = fontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = fontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = fontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = fontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = fontFamily),
)
