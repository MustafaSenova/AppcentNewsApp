package com.appcentnewsapp.mustafasenova

import android.app.Application
import android.content.Context

class AppcentNewsApp : Application(){

    companion object {
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}