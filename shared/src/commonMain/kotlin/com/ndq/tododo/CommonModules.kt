package com.ndq.tododo

import com.ndq.tododo.repositories.PreferencesDao
import com.ndq.tododo.repositories.TodoDao
import com.ndq.tododo.screens.edit.EditViewModel
import com.ndq.tododo.screens.home.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun commonModules(): Module = module {
    single<TodoDao> { get<AppDatabase>().getTodoDao() }
    single<PreferencesDao> { get<AppDatabase>().getPreferencesDao() }
    singleOf(::AppViewModel)
    singleOf(::HomeViewModel)
    factory { (id: Long?) -> EditViewModel(get(), id) }
}
