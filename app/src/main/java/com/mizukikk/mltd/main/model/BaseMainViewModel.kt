package com.mizukikk.mltd.main.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mizukikk.mltd.Inject

open class BaseMainViewModel(application: Application) : AndroidViewModel(application) {
    protected val repository by lazy {
        Inject.providerMLTDRepository()
    }
}