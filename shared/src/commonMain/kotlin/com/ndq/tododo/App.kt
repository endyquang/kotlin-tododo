package com.ndq.tododo

import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ndq.tododo.screens.edit.Edit
import com.ndq.tododo.screens.edit.EditScreen
import com.ndq.tododo.screens.edit.EditViewModel
import com.ndq.tododo.screens.home.Home
import com.ndq.tododo.screens.home.HomeScreen
import com.ndq.tododo.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        val isSystemDark = isSystemInDarkTheme()
        var isDark by remember { mutableStateOf(isSystemDark) }

        AppTheme(darkTheme = isDark) {
            NavHost(navController = navController, startDestination = Home) {
                composable<Home> { HomeScreen() }
                dialog<Edit> { backStackEntry ->
                    val edit: Edit = backStackEntry.toRoute()
                    val viewModel: EditViewModel = koinInject { parametersOf(edit.id) }
                    EditScreen(viewModel)
                }
            }
        }
    }
}
