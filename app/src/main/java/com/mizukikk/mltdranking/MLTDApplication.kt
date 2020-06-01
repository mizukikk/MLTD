package com.mizukikk.mltdranking

import android.app.Application
import android.content.Context

class MLTDApplication : Application() {


    companion object {
        private var instance: Application? = null
        val applicationContext by lazy { instance!!.applicationContext }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}