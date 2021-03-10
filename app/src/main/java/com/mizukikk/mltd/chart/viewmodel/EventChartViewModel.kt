package com.mizukikk.mltd.chart.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mizukikk.mltd.Inject
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.obj.EventPoint
import com.mizukikk.mltd.chart.model.EventChartData
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import retrofit2.Call

class EventChartViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val TAG = EventChartViewModel::class.java.simpleName
    }

    private val repository by lazy { Inject.providerMLTDRepository() }
    val eventBordersLiveData = MutableLiveData<List<EventPoint>>()

    fun getEventBorders(data: EventChartData) {
        val callBack = object : ResponseCallBack<List<EventPoint>> {
            override fun success(response: List<EventPoint>) {
                eventBordersLiveData.postValue(response)
            }

            override fun fail(errorMessage: String, errorCode: Int?, call: Call<List<EventPoint>>) {}
        }
        getEventBordersLog(data, callBack)
    }

    private fun getEventBordersLog(data: EventChartData, callBack: ResponseCallBack<List<EventPoint>>) {
        if (data.isAnivEvent) {
            repository.getAnivIdolRankLog(data.eventId, PreferencesHelper.anivIdolId, callBack)
        } else {
            repository.getEventRankLog(data.eventId, data.type!!, data.ranks!!, callBack)
        }
    }

}