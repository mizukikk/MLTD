package com.mizukikk.mltdranking.data.remote

import com.mizukikk.mltdranking.api.ApiCallBack
import com.mizukikk.mltdranking.api.ResponseCallBack
import com.mizukikk.mltdranking.api.RetrofitProvider
import com.mizukikk.mltdranking.api.RetrofitService
import com.mizukikk.mltdranking.api.response.EventResponse
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