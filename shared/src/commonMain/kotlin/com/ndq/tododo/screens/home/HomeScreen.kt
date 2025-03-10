package com.ndq.tododo.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ndq.tododo.LocalNavController
import com.ndq.tododo.LocalPreferences
import com.ndq.tododo.composables.TodoListItem
import com.ndq.tododo.models.AppTheme
import com.ndq.tododo.models.Preferences
import com.ndq.tododo.models.TodoStatus
import com.ndq.tododo.screens.edit.Edit
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object Home

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val navController = LocalNavController.current
    val preferences = LocalPreferences.current
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val todos by viewModel.todos.collectAsStateWithLifecycle()
    val status by viewModel.status.collectAsStateWithLifecycle()

    HomeLayout(
        menuButton = {
            IconButton(onClick = { println("Menu") }) {
                Icon(Icons.Filled.Menu, "Menu")
            }
        },
        searchInput = { modifier ->
            TextField(
                value = "",
                onValueChange = { println(it) },
                modifier = modifier,
                placeholder = { Text("Search") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                )
            )
        },
        profileButton = {
            IconButton(onClick = {
                viewModel.updatePreferences(
                    preferences.copy(
                        theme = if (preferences.isDarkTheme) AppTheme.LIGHT else AppTheme.DARK
                    )
                )
            }) {
                Icon(
                    if (preferences.isDarkTheme) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                    "Theme"
                )
            }
        },
        floatingAddButton = { modifier ->
            FloatingActionButton(
                shape = CircleShape,
                modifier = modifier,
                onClick = {
                    navController.navigate(route = Edit())
                }
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(
                        horizontal = 12.dp,
                        vertical = 4.dp
                    )
                ) {
                    TodoStatus.entries.forEach {
                        FilterChip(
                            onClick = { viewModel.setStatus(it) },
                            label = { Text(it.displayName) },
                            selected = status == it,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

            if (isLoading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(20.dp).width(24.dp).height(24.dp),
                        strokeWidth = 3.dp,
                    )
                }
            } else if (todos.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            "Empty",
                            modifier = Modifier.align(Alignment.Center).padding(20.dp),
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                    }
                }
            } else {
                items(todos, key = { it.id }) { item ->
                    TodoListItem(
                        item = item,
                        onCompletedChanged = { viewModel.toggleDone(item) },
                        onPressed = {
                            navController.navigate(route = Edit(id = item.id))
                        },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    )
                }
            }
        }
    }
}
