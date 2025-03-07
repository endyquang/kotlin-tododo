package com.ndq.tododo.screens.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ndq.tododo.LocalNavController
import com.ndq.tododo.composables.DateField
import com.ndq.tododo.models.Todo
import kotlinx.serialization.Serializable

@Serializable
data class Edit(val id: Long? = null)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(viewModel: EditViewModel) {
    val navController = LocalNavController.current
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val initialTodo by viewModel.initialTodo.collectAsStateWithLifecycle()
    val draft by viewModel.draft.collectAsStateWithLifecycle()
    val initialItem = initialTodo ?: Todo.defaultTodo
    val isAddNew = initialTodo == null

    val isChanged = initialItem.title != draft.title.trim()
            || initialItem.scheduleAt != draft.scheduleAt
            || initialItem.description != draft.description.trim()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isLoading) "" else if (isAddNew) "Add new" else "Edit"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    if (!isLoading && (isChanged || isAddNew))
                        FilledTonalButton(
                            onClick = {
                                viewModel.save(
                                    initialItem.copy(
                                        title = draft.title.trim(),
                                        description = draft.description.trim(),
                                        scheduleAt = draft.scheduleAt,
                                    ),
                                )
                                navController.popBackStack()
                            },
                            shape = RoundedCornerShape(4.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp),
                            modifier = Modifier.height(36.dp).padding(horizontal = 12.dp)
                        ) {
                            Text(
                                "Save",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                }
            )
        },
    ) { innerPadding ->
        Box(
            Modifier.fillMaxSize().padding(innerPadding),
        ) {
            Column(
                Modifier.fillMaxHeight().imePadding(),
            ) {
                val focusRequester = remember { FocusRequester() }
                val desFocusRequester = remember { FocusRequester() }

                TextField(
                    value = draft.title,
                    onValueChange = { viewModel.updateDraft(draft.copy(title = it)) },
                    placeholder = { Text("Title") },
                    modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                DateField(value = draft.scheduleAt, onChanged = {
                    viewModel.updateDraft(draft.copy(scheduleAt = it))
                    desFocusRequester.requestFocus()
                })

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = draft.description,
                    onValueChange = {
                        viewModel.updateDraft(draft.copy(description = it))
                    },
                    placeholder = { Text("Description") },
                    modifier = Modifier.fillMaxSize().focusRequester(desFocusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                )

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }
        }
    }
}
