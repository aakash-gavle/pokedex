package com.aakash.android

import android.app.Application
import com.aakash.android.di.AppModules

class App : Application() {
    lateinit var appModules: AppModules
        private set

    override fun onCreate() {
        super.onCreate()
        appModules = AppModules()
    }
}
