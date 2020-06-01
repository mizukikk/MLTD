package com.mizukikk.mltdranking.data.remote

import com.mizukikk.mltdranking.api.ResponseCallBack
import com.mizukikk.mltdranking.api.response.EventResponse

interface RemoteDataSource {
    fun getAllEvent(callBack: ResponseCallBack<List<EventResponse>>)
}