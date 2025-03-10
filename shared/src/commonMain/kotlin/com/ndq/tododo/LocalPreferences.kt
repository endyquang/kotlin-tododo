package com.ndq.tododo

import androidx.compose.runtime.compositionLocalOf
import com.ndq.tododo.models.Preferences

val LocalPreferences = compositionLocalOf<Preferences> {
    error("No LocalPreferences provided")
}
