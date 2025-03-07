package com.ndq.tododo

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            commonModules() + platformModules()
        )
    }
}

fun initKoinIos() = initKoin(appDeclaration = {})
