package com.ndq.tododo.models

enum class TodoStatus(val displayName: String) {
    TODO("# TODO"),
    COMPLETED("# DONE"),
    CANCELLED("# CANCELLED")
}
