package com.ndq.tododo

import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModules(): Module = module {
    single<AppDatabase> {
        val builder = getDatabaseBuilder(ctx = get())
        getRoomDatabase(builder)
    }
}
