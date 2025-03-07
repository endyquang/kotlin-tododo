package com.ndq.tododo.android

import android.app.Application
import com.ndq.tododo.initKoin
import org.koin.android.ext.koin.androidContext

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin (
            appDeclaration = { androidContext(this@MainApplication) },
        )
    }
}
