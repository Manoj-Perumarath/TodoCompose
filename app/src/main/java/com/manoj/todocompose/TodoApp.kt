package com.manoj.todocompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}