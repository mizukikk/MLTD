package com.mizukikk.mltd.data.remote

import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.EventResponse

interface RemoteDataSource {
    fun getAllEvent(callBack: ResponseCallBack<List<EventResponse>>)
}