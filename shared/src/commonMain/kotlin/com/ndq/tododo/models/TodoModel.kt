package com.ndq.tododo.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class TodoModel(
    val id: Long = Clock.System.now().toEpochMilliseconds(),
    val title: String,
    val description: String = "",
    val doneAt: Instant? = null,
    val cancelledAt: Instant? = null,
    val scheduleAt: Instant? = null,
    val createdAt: Instant = Clock.System.now(),
) {
    val isDone: Boolean
        get() = doneAt != null

    val isCancelled: Boolean
        get() = cancelledAt != null

    val status: TodoStatus
        get() = if (isDone) TodoStatus.COMPLETED
        else if (isCancelled) TodoStatus.CANCELLED
        else TodoStatus.TODO

    val updatedAt: Instant
        get() = cancelledAt ?: doneAt ?: createdAt

    companion object {
        val defaultTodo = TodoModel(title = "")
    }
}
