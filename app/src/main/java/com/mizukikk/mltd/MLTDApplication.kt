package com.mizukikk.mltd

import android.app.Application

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