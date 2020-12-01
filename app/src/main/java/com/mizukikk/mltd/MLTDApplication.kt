package com.mizukikk.mltd

import android.app.Application
import com.facebook.stetho.Stetho

class MLTDApplication : Application() {


    companion object {
        private var instance: Application? = null
        val appContext by lazy { instance!!.applicationContext }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }
}