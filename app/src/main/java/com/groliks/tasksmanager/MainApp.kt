package com.groliks.tasksmanager

import android.app.Application
import android.content.Context
import com.groliks.tasksmanager.di.AppComponent
import com.groliks.tasksmanager.di.DaggerAppComponent

class MainApp : Application() {
    lateinit var appComponent: AppComponent
        private set
    var authToken: String? = null

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()

        super.onCreate()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApp -> appComponent
        else -> applicationContext.appComponent
    }

var Context.authToken: String?
    get() = when (this) {
        is MainApp -> authToken
        else -> applicationContext.authToken
    }
    set(value) = when (this) {
        is MainApp -> authToken = "Bearer $value"
        else -> applicationContext.authToken = value
    }