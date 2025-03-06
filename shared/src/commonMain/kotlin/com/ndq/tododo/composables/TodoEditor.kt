package com.ndq.tododo.composables

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ndq.tododo.models.TodoModel
import com.ndq.tododo.services.TodoService
import kotlinx.datetime.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditor(
    id: Long?,
    onPressedBack: () -> Unit,
) {
    val service = TodoService()
    val initialItem = (if (id == null) null else service.getById(id)) ?: TodoModel.defaultTodo
    var title by rememberSaveable { mutableStateOf(initialItem.title) }
    var scheduleAt by rememberSaveable { mutableStateOf<Instant?>(initialItem.scheduleAt) }
    var description by rememberSaveable { mutableStateOf(initialItem.description) }

    val isChanged = initialItem.title != title.trim()
            || initialItem.scheduleAt != scheduleAt
            || initialItem.description != description.trim()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit") },
                navigationIcon = {
                    IconButton(onClick = onPressedBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    if (isChanged)
                        FilledTonalButton(
                            onClick = {
                                service.save(
                                    initialItem.copy(
                                        title = title,
                                        description = description,
                                        scheduleAt = scheduleAt,
                                    ),
                                )
                                onPressedBack()
                            },
                            shape = RoundedCornerShape(4.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp),
                            modifier = Modifier.height(36.dp).padding(horizontal = 12.dp)
                        ) {
                            Text("Save", style = MaterialTheme.typography.bodyLarge)
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
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Title") },
                    modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                DateField(value = scheduleAt, onChanged = {
                    scheduleAt = it
                    desFocusRequester.requestFocus()
                })

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = description,
                    onValueChange = { description = it },
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
