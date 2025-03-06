package com.ndq.tododo

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.BackNavigationBehavior
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.ndq.tododo.composables.AppBar
import com.ndq.tododo.composables.TodoEditor
import com.ndq.tododo.composables.TodoListItem
import com.ndq.tododo.models.TodoStatus
import com.ndq.tododo.services.TodoService
import com.ndq.tododo.theme.AppTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
@Preview
fun App() {
    val isSystemDark = isSystemInDarkTheme()
    var isDark by remember { mutableStateOf(isSystemDark) }
    val todoService = TodoService()

    AppTheme(darkTheme = isDark) {
        var activeTodoStatus by remember { mutableStateOf(TodoStatus.TODO) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        val navigator = rememberListDetailPaneScaffoldNavigator<Long>()

        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            IconButton(
                                onClick = {
                                    scope.launch { drawerState.close() }
                                },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Icon(Icons.Filled.Close, "Close")
                            }
                            Column {
                                TodoStatus.entries.forEach {
                                    val interactionSource =
                                        remember { MutableInteractionSource() }
                                    val indication = LocalIndication.current
                                    Box(
                                        modifier = Modifier.width(260.dp).clickable(
                                            interactionSource = interactionSource,
                                            indication = indication,
                                        ) {
                                            scope.launch { drawerState.close() }
                                            activeTodoStatus = it
                                        }
                                    ) {
                                        Text(
                                            it.displayName,
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                color = if (activeTodoStatus == it)
                                                    MaterialTheme.colorScheme.primary
                                                else
                                                    MaterialTheme.colorScheme.onSurface
                                            ),
                                            modifier = Modifier.padding(
                                                vertical = 20.dp,
                                                horizontal = 20.dp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            AppBar(
                                title = activeTodoStatus.displayName,
                                scrollBehavior = scrollBehavior,
                                isDark = isDark,
                                onToggleTheme = { isDark = !isDark },
                                onPressedMenu = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(
                            Modifier.fillMaxSize().padding(innerPadding),
                        ) {

                            val listState = rememberLazyListState()
                            val todoItems = todoService.getAllByStatus(activeTodoStatus)
                            LazyColumn(
                                state = listState,
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(20.dp)
                            ) {
                                items(todoItems, key = { it.id }) { item ->
                                    TodoListItem(
                                        item = item,
                                        onCompletedChanged = {
                                            todoService.toggleDoneStatus(item)
                                        },
                                        onPressed = {
                                            navigator.navigateTo(
                                                ListDetailPaneScaffoldRole.Detail,
                                                item.id
                                            )
                                        }
                                    )
                                    Spacer(
                                        modifier = Modifier.height(20.dp)
                                    )
                                }
                            }

                            FloatingActionButton(
                                shape = CircleShape,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 28.dp,
                                    ),
                                onClick = {
                                    navigator.navigateTo(
                                        ListDetailPaneScaffoldRole.Detail,
                                        null
                                    )
                                }
                            ) {
                                Icon(Icons.Filled.Add, "Add")
                            }
                        }
                    }
                }
            },
            detailPane = {
                AnimatedPane {
                    val id = navigator.currentDestination?.content;
                    TodoEditor(
                        id = id,
                        onPressedBack = {
                            navigator.navigateBack(BackNavigationBehavior.PopLatest)
                        }
                    )
                }
            }
        )
    }
}
