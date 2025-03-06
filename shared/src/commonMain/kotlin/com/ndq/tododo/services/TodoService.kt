package com.ndq.tododo.services

import com.ndq.tododo.models.TodoModel
import com.ndq.tododo.models.TodoStatus
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.days

class TodoService {
    fun getAllByStatus(status: TodoStatus): List<TodoModel> {
        return list.filter { item -> item.status == status }
    }

    fun getById(id: Long): TodoModel? {
        return list.find { it.id == id }
    }

    fun save(item: TodoModel): Unit {
        val index = list.indexOfFirst { it.id == item.id }
        if (index == -1) {
            list.add(0, item)
            return
        }
        list[index] = item
    }

    fun toggleDoneStatus(item: TodoModel) {
        save(
            item.copy(
                doneAt = if (item.doneAt == null) Clock.System.now() else null
            )
        )
    }

    companion object {
        private val now = Clock.System.now()

        private val list = mutableListOf(
            TodoModel(
                id = 1,
                title = "Buy groceries",
                description = "Milk, eggs, bread, and fruits",
                createdAt = now,
            ),
            TodoModel(
                id = 2,
                title = "Take a bath",
                description = "Monthly bath",
                createdAt = now.minus(1.days),
            ),
            TodoModel(
                id = 3,
                title = "Do the dishes",
                createdAt = now.minus(2.days),
            ),
            TodoModel(
                id = 4,
                title = "Brush teeth",
                createdAt = now.minus(3.days),
            ),
            TodoModel(
                id = 5,
                title = "Eat lunch",
                createdAt = now.minus(4.days),
            ),
            TodoModel(
                id = 6,
                title = "Meditate",
                createdAt = now.minus(5.days),
            ),
            TodoModel(
                id = 7,
                title = "Workout",
                createdAt = now.minus(6.days),
            ),
            TodoModel(
                id = 8,
                title = "Breathe",
                createdAt = now.minus(7.days),
            ),
            TodoModel(
                id = 9,
                title = "Go out",
                createdAt = now.minus(8.days),
            ),
            TodoModel(
                id = 10,
                title = "Wake up",
                createdAt = now.minus(9.days),
            ),
            TodoModel(
                id = 11,
                title = "Code",
                createdAt = now.minus(7.days),
                doneAt = now.minus(6.days),
            ),
            TodoModel(
                id = 12,
                title = "Learn Kotlin",
                createdAt = now.minus(8.days),
                doneAt = now.minus(8.days),
            ),
            TodoModel(
                id = 13,
                title = "Learn KMP",
                createdAt = now.minus(9.days),
                cancelledAt = now.minus(8.days),
            ),
        )
    }
}
