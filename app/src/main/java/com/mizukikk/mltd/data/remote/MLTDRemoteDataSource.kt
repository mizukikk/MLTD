package com.mizukikk.mltd.data.remote

import com.mizukikk.mltd.api.ApiCallBack
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.RetrofitProvider
import com.mizukikk.mltd.api.RetrofitService
import com.mizukikk.mltd.api.response.EventResponse
import retrofit2.Call

object MLTDRemoteDataSource : RemoteDataSource {

    private val eventService by lazy {
        RetrofitProvider.instance.create(RetrofitService.Event::class.java)
    }


    override fun getAllEvent(callBack: ResponseCallBack<List<EventResponse>>) {
        val call = eventService.getAllEvent()
        call.enqueue(object : ApiCallBack<List<EventResponse>>() {
            override fun apiSuccess(
                response: List<EventResponse>,
                call: Call<List<EventResponse>>
            ) {
                callBack.success(response, call)
            }

            override fun apiFail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<EventResponse>>
            ) {
                callBack.fail(errorMessage, errorCode, call)
            }
        })
    }
}