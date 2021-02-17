package com.mizukikk.mltd.data.source.remote

import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.obj.EventBorders
import com.mizukikk.mltd.api.obj.EventPoint
import com.mizukikk.mltd.api.response.CardResponse
import com.mizukikk.mltd.api.response.EventResponse
import com.mizukikk.mltd.api.response.GetLastPointResponse

interface RemoteDataSource {
    fun downloadAllCard(callBack: ResponseCallBack<List<CardResponse>>)
    fun checkUpdate(lastIdolId: Int, callBack: ResponseCallBack<List<CardResponse>>)
    fun getEventList(callBack: ResponseCallBack<List<EventResponse>>)
    fun getLastEventPoints(eventId: Int, callBack: ResponseCallBack<GetLastPointResponse>)
    fun getEventBorders(id: Int, callBack: ResponseCallBack<EventBorders>)
    fun getEventPoint(id: Int, borders: String, callBack: ResponseCallBack<EventPoint>)
    fun getAnivIdolRankPoint(id: Int, idolId: Int, callBack: ResponseCallBack<List<EventPoint>>)
}