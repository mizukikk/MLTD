package com.mizukikk.mltd.data.source.remote

import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.api.response.Event

interface RemoteDataSource {
    fun downloadAllCard(callBack: ResponseCallBack<List<Card.CardResponse>>)
    fun checkUpdate(lastIdolId: Int, callBack: ResponseCallBack<List<Card.CardResponse>>)
    fun getEventList(callBack:ResponseCallBack<List<Event.EventResponse>>)
}