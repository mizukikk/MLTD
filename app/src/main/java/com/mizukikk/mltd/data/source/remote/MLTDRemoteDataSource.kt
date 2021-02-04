package com.mizukikk.mltd.data.source.remote

import com.mizukikk.mltd.api.ApiCallBack
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.RetrofitProvider
import com.mizukikk.mltd.api.RetrofitService
import com.mizukikk.mltd.api.obj.EventBorders
import com.mizukikk.mltd.api.obj.EventPoint
import com.mizukikk.mltd.api.response.CardResponse
import com.mizukikk.mltd.api.response.EventResponse
import com.mizukikk.mltd.api.response.GetLastPointResponse
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import retrofit2.Call

object MLTDRemoteDataSource : RemoteDataSource {

    private val eventService by lazy {
        RetrofitProvider.instance.create(RetrofitService.EventService::class.java)
    }
    private val cardService by lazy {
        RetrofitProvider.instance.create(RetrofitService.CardService::class.java)
    }

    override fun downloadAllCard(callBack: ResponseCallBack<List<CardResponse>>) {
        val lang = PreferencesHelper.apiLanguage
        val call = cardService.getAllCard(lang)
        call.enqueue(object : ApiCallBack<List<CardResponse>>() {
            override fun apiSuccess(
                response: List<CardResponse>,
                call: Call<List<CardResponse>>
            ) {
                response.forEach {
                    it.lang = lang
                }
                callBack.success(response)
            }

            override fun apiFail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<CardResponse>>
            ) {
                callBack.fail(errorMessage, errorCode, call)
            }
        })
    }

    override fun checkUpdate(lastIdolId: Int, callBack: ResponseCallBack<List<CardResponse>>) {
        val nextId = lastIdolId + 1
        val call = cardService.getCard(PreferencesHelper.apiLanguage, nextId)
        call.enqueue(object : ApiCallBack<List<CardResponse>>() {
            override fun apiSuccess(
                response: List<CardResponse>,
                call: Call<List<CardResponse>>
            ) {
                callBack.success(response)
            }

            override fun apiFail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<CardResponse>>
            ) {
                callBack.fail(errorMessage, errorCode, call)
            }
        })
    }

    override fun getEventList(callBack: ResponseCallBack<List<EventResponse>>) {
        val call = eventService.getAllEvent()
        call.enqueue(object : ApiCallBack<List<EventResponse>>() {
            override fun apiSuccess(
                response: List<EventResponse>,
                call: Call<List<EventResponse>>
            ) {
                callBack.success(response)
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

    override fun getLastEventPoints(
        eventId: Int,
        callBack: ResponseCallBack<GetLastPointResponse>
    ) {
        val call = eventService.getLastEventPoints(eventId)
        call.enqueue(object : ApiCallBack<GetLastPointResponse>() {
            override fun apiSuccess(
                response: GetLastPointResponse,
                call: Call<GetLastPointResponse>
            ) {
                callBack.success(response)
            }

            override fun apiFail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<GetLastPointResponse>
            ) {
                callBack.fail(errorMessage, errorCode, call)
            }
        })
    }

    override fun getEventBorders(id: Int, callBack: ResponseCallBack<EventBorders>) {
        val call = eventService.getEventBorders(id)
        call.enqueue(object : ApiCallBack<EventBorders>() {
            override fun apiSuccess(response: EventBorders, call: Call<EventBorders>) {
                callBack.success(response)
            }

            override fun apiFail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<EventBorders>
            ) {
                callBack.fail(errorMessage, errorCode, call)
            }
        })
    }

    override fun getEventPoint(
        id: Int,
        borders: String,
        callBack: ResponseCallBack<EventPoint>
    ) {
        val call = eventService.getEventPoint(id, borders)
        call.enqueue(object : ApiCallBack<EventPoint>() {
            override fun apiSuccess(response: EventPoint, call: Call<EventPoint>) {
                callBack.success(response)
            }

            override fun apiFail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<EventPoint>
            ) {
                callBack.fail(errorMessage, errorCode, call)
            }
        })
    }
}