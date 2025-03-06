package com.ndq.tododo.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    isDark: Boolean,
    onToggleTheme: () -> Unit,
    onPressedMenu: () -> Unit,
) {
    MediumTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(title)
        },
        navigationIcon = {
            IconButton(onClick = onPressedMenu) {
                Icon(Icons.Filled.Menu, "Menu")
            }
        },
        actions = {
            IconButton(onClick = onToggleTheme) {
                Icon(
                    if (isDark) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                    "Theme"
                )
            }
        }
    )
}