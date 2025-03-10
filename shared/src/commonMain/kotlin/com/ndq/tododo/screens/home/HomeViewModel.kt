package com.ndq.tododo.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndq.tododo.models.Preferences
import com.ndq.tododo.models.Todo
import com.ndq.tododo.models.TodoStatus
import com.ndq.tododo.repositories.PreferencesDao
import com.ndq.tododo.repositories.TodoDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class HomeViewModel(
    private val todoDao: TodoDao,
    private val preferencesDao: PreferencesDao,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _status = MutableStateFlow(TodoStatus.TODO)
    val status = _status.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val todos: StateFlow<List<Todo>> = status.flatMapLatest {
        println(it)
        todoDao.getAllByStatus(it)
    }
        .onEach {
            if (_isLoading.value) {
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

    fun setStatus(status: TodoStatus) {
        _status.value = status
    }

    fun updatePreferences(preferences: Preferences) {
        viewModelScope.launch {
            preferencesDao.upsert(preferences)
        }
    }
}
