package com.ndq.tododo.theme

import com.ndq.tododo.R
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font

actual fun getFontFamily() = FontFamily(
    Font(
        googleFont = GoogleFont("Noto Sans Mono"),
        fontProvider = GoogleFont.Provider(
            providerAuthority = "com.google.android.gms.fonts",
            providerPackage = "com.google.android.gms",
            certificates = R.array.com_google_android_gms_fonts_certs
        ),
    )
)
