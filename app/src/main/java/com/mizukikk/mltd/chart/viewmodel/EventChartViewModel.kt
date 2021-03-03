package com.mizukikk.mltd.chart.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mizukikk.mltd.Inject

class EventChartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository by lazy { Inject.providerMLTDRepository() }

    fun getEventBorders(id: Int, borders: String){
        repository
    }

}