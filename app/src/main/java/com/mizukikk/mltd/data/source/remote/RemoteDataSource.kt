package com.mizukikk.mltd.data.source.remote

import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.api.response.Event
import com.mizukikk.mltd.api.response.GetLastPointResponse

interface RemoteDataSource {
    fun downloadAllCard(callBack: ResponseCallBack<List<Card.CardResponse>>)
    fun checkUpdate(lastIdolId: Int, callBack: ResponseCallBack<List<Card.CardResponse>>)
    fun getEventList(callBack: ResponseCallBack<List<Event.EventResponse>>)
    fun getLastEventPoints(eventId: Int, callBack: ResponseCallBack<GetLastPointResponse>)
    fun getEventBorders(id: Int, callBack: ResponseCallBack<Event.EventBorders>)
    fun getEventPoint(id: Int, borders: String, callBack: ResponseCallBack<Event.EventPoint>)
}