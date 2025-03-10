package com.ndq.tododo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Preferences(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val theme: AppTheme,
) {
    val isDarkTheme: Boolean get() = theme == AppTheme.DARK
}
