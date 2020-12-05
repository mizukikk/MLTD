package com.mizukikk.mltd.main.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mizukikk.mltd.Inject
import java.lang.Exception

open class BaseMainViewModel(application: Application) : AndroidViewModel(application) {
    protected val repository by lazy {
        Inject.providerMLTDRepository()
    }
    private val context = application.applicationContext

    protected fun getAssetsDataText(fileName: String): String {
        return try {
            context.resources.assets
                .open(fileName)
                .bufferedReader()
                .readText()
        } catch (e: Exception) {
            ""
        }
    }
}