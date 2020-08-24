package com.mizukikk.mltd.data.source.remote

import com.mizukikk.mltd.api.ApiCallBack
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.RetrofitProvider
import com.mizukikk.mltd.api.RetrofitService
import com.mizukikk.mltd.api.response.Event
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import retrofit2.Call

object MLTDRemoteDataSource : RemoteDataSource {

    private val eventService by lazy {
        RetrofitProvider.instance.create(RetrofitService.EventService::class.java)
    }


    override fun getAllEvent(callBack: ResponseCallBack<List<Event.EventResponse>>) {
        val call = eventService.getAllEvent(PreferencesHelper.apiLanguage)
        call.enqueue(object : ApiCallBack<List<Event.EventResponse>>() {
            override fun apiSuccess(
                response: List<Event.EventResponse>,
                call: Call<List<Event.EventResponse>>
            ) {
                callBack.success(response, call)
            }

            override fun apiFail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<Event.EventResponse>>
            ) {
                callBack.fail(errorMessage, errorCode, call)
            }
        })
    }
}