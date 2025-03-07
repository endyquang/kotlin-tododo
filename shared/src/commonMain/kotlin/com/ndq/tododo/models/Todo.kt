package com.ndq.tododo.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val doneAt: Long? = null,
    val cancelledAt: Long? = null,
    val scheduleAt: Long? = null,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
) {
    val isDone: Boolean
        get() = doneAt != null

    val isCancelled: Boolean
        get() = cancelledAt != null

    val status: TodoStatus
        get() = if (isDone) TodoStatus.COMPLETED
        else if (isCancelled) TodoStatus.CANCELLED
        else TodoStatus.TODO

    val updatedAt: Long
        get() = cancelledAt ?: doneAt ?: createdAt

    companion object {
        val defaultTodo = Todo(title = "")
    }
}
