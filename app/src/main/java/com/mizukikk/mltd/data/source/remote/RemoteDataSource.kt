package com.mizukikk.mltd.data.source.remote

import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Event

interface RemoteDataSource {
    fun getAllEvent(callBack: ResponseCallBack<List<Event.EventResponse>>)
}