package com.ndq.tododo.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndq.tododo.models.Todo
import com.ndq.tododo.repositories.TodoDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class HomeViewModel(private val todoDao: TodoDao) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val todos: StateFlow<List<Todo>> = todoDao.getAllFlow()
        .onEach {
            if (!_isLoading.value) {
                _isLoading.value = false
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    fun toggleDone(todo: Todo) {
        viewModelScope.launch {
            todoDao.update(
                todo.copy(
                    doneAt = if (todo.isDone) null else Clock.System.now().toEpochMilliseconds(),
                    cancelledAt = null,
                ),
            )
        }
    }
}
