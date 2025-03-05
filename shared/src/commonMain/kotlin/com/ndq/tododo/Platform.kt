package com.ndq.tododo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform