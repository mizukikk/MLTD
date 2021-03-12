package com.mizukikk.mltd.main.event.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mizukikk.mltd.R
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.EventResponse
import com.mizukikk.mltd.main.event.model.EventDetailData
import com.mizukikk.mltd.main.viewmodel.BaseMainViewModel
import retrofit2.Call

class EventListViewModel(application: Application) : BaseMainViewModel(application) {

    val eventListLiveData = MutableLiveData<List<EventResponse>>()

    private var isCheckApiRunning = false

    fun getEventList() {
        repository.getEventList(object : ResponseCallBack<List<EventResponse>> {
            override fun success(response: List<EventResponse>) {
                eventListLiveData.postValue(response.sortedBy { it.sort }.reversed())
            }

            override fun fail(errorMessage: String, errorCode: Int?, call: Call<List<EventResponse>>) {
                eventListLiveData.postValue(mutableListOf())
                showToast(getString(R.string.service_error))
            }
        })
    }

    inline fun goToEventPage(eventData: EventResponse, action: (EventDetailData) -> Unit) {
        action.invoke(EventDetailData(eventData))
    }
}