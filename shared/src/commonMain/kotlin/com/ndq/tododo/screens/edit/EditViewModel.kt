package com.ndq.tododo.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndq.tododo.models.Todo
import com.ndq.tododo.models.TodoStatus
import com.ndq.tododo.repositories.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

data class DraftTodo(
    val title: String = "",
    val description: String = "",
    val scheduleAt: Long? = null,
)

class EditViewModel(private val todoDao: TodoDao, private val id: Long?) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _initialTodo = MutableStateFlow<Todo?>(null)
    val initialTodo = _initialTodo.asStateFlow()

    private val _draft = MutableStateFlow<DraftTodo>(DraftTodo())
    val draft = _draft.asStateFlow()

    fun save(todo: Todo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (id == null) {
                    todoDao.insert(todo)
                } else {
                    todoDao.update(todo)
                }
            }
        }
    }

    fun updateDraft(newDraft: DraftTodo) {
        _draft.value = newDraft
    }

    fun updateStatus(status: TodoStatus) {
        if (isLoading.value) {
            return
        }
        val todo = initialTodo.value ?: return
        val now = Clock.System.now().toEpochMilliseconds()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
            todoDao.update(
                todo.copy(
                    doneAt = if (status == TodoStatus.DONE) now else null,
                    cancelledAt = if (status == TodoStatus.CANCELLED) now else null,
                )
            )
                }
        }
    }

    fun delete() {
        if (isLoading.value) {
            return
        }
        val todo = initialTodo.value ?: return
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                todoDao.delete(todo.id)
            }
        }
    }

    init {
        viewModelScope.launch {
            if (id != null) {
                val todo = todoDao.getById(id)
                _initialTodo.value = todo
                if (todo != null) {
                    _draft.value = DraftTodo(
                        title = todo.title,
                        description = todo.description,
                        scheduleAt = todo.scheduleAt,
                    )
                }
            }
            _isLoading.value = false
        }
    }
}
