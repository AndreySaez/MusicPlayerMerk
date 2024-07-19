package com.example.musicplayermerk

import android.app.Application
import android.content.Context
import com.example.musicplayermerk.di.AppComponent
import com.example.musicplayermerk.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }


