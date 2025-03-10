package com.ndq.tododo

import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ndq.tododo.models.AppTheme
import com.ndq.tododo.models.Preferences
import com.ndq.tododo.screens.edit.Edit
import com.ndq.tododo.screens.edit.EditScreen
import com.ndq.tododo.screens.edit.EditViewModel
import com.ndq.tododo.screens.home.Home
import com.ndq.tododo.screens.home.HomeScreen
import com.ndq.tododo.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App(viewModel: AppViewModel = koinViewModel()) {
    val preferencesState = viewModel.preferences.collectAsStateWithLifecycle()
    val preferences = preferencesState.value ?: Preferences(
        theme = if (isSystemInDarkTheme()) AppTheme.DARK else AppTheme.LIGHT
    )
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalPreferences provides (preferences)
    ) {
        AppTheme(darkTheme = preferences.isDarkTheme) {
            NavHost(navController = navController, startDestination = Home) {
                composable<Home> { HomeScreen() }
                dialog<Edit> { backStackEntry ->
                    val edit: Edit = backStackEntry.toRoute()
                    val editViewModel: EditViewModel = koinInject { parametersOf(edit.id) }
                    EditScreen(editViewModel)
                }
            }
        }
    }
}
