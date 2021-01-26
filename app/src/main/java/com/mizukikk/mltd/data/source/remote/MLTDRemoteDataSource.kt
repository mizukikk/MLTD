package com.mizukikk.mltd.data.source.remote

import com.mizukikk.mltd.api.ApiCallBack
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.RetrofitProvider
import com.mizukikk.mltd.api.RetrofitService
import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.api.response.Event
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import retrofit2.Call

object MLTDRemoteDataSource : RemoteDataSource {

    private val eventService by lazy {
        RetrofitProvider.instance.create(RetrofitService.EventService::class.java)
    }
    private val cardService by lazy {
        RetrofitProvider.instance.create(RetrofitService.CardService::class.java)
    }

    override fun downloadAllCard(callBack: ResponseCallBack<List<Card.CardResponse>>) {
        val lang = PreferencesHelper.apiLanguage
        val call = cardService.getAllCard(lang)
        call.enqueue(object : ApiCallBack<List<Card.CardResponse>>() {
            override fun apiSuccess(
                response: List<Card.CardResponse>,
                call: Call<List<Card.CardResponse>>
            ) {
                response.forEach {
                    it.lang = lang
                }
                callBack.success(response)
            }

            override fun apiFail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<Card.CardResponse>>
            ) {
                callBack.fail(errorMessage, errorCode, call)
            }
        })
    }

    override fun checkUpdate(lastIdolId: Int, callBack: ResponseCallBack<List<Card.CardResponse>>) {
        val nextId = lastIdolId + 1
        val call = cardService.getCard(PreferencesHelper.apiLanguage, nextId)
        call.enqueue(object : ApiCallBack<List<Card.CardResponse>>() {
            override fun apiSuccess(
                response: List<Card.CardResponse>,
                call: Call<List<Card.CardResponse>>
            ) {
                callBack.success(response)
            }

            override fun apiFail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<Card.CardResponse>>
            ) {
                callBack.fail(errorMessage, errorCode, call)
            }
        })
    }

    override fun getEventList(callBack: ResponseCallBack<List<Event.EventResponse>>) {
        val call = eventService.getAllEvent()
        call.enqueue(object : ApiCallBack<List<Event.EventResponse>>() {
            override fun apiSuccess(
                response: List<Event.EventResponse>,
                call: Call<List<Event.EventResponse>>
            ) {
                callBack.success(response)
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