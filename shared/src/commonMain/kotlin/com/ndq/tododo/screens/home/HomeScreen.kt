package com.ndq.tododo.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
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
import com.ndq.tododo.composables.TodoListItem
import com.ndq.tododo.screens.edit.Edit
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object Home

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val navController = LocalNavController.current
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val todos by viewModel.todos.collectAsStateWithLifecycle()

    HomeLayout(
        menuButton = {
            IconButton(onClick = { println("Menu") }) {
                Icon(Icons.Filled.Menu, "Menu")
            }
        },
        searchInput = {modifier ->
            TextField(
                value = "",
                onValueChange = { println(it) },
                modifier = modifier,
                placeholder = { Text("Search") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        },
        profileButton = {
            IconButton(onClick = { println("Profile") }) {
                Icon(Icons.Filled.Person, "Profile")
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

        if (isLoading) {
            CircularProgressIndicator()
        } else if (todos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    "Empty",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        } else {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(20.dp),
            ) {
                items(todos, key = { it.id }) { item ->
                    TodoListItem(
                        item = item,
                        onCompletedChanged = { viewModel.toggleDone(item) },
                        onPressed = {
                            navController.navigate(route = Edit(id = item.id))
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }
            }
        }
    }
}
