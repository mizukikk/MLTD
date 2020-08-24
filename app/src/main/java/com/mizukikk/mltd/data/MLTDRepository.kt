package com.mizukikk.mltd.data

import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.EventResponse
import com.mizukikk.mltd.data.remote.MLTDRemoteDataSource
import com.mizukikk.mltd.data.remote.RemoteDataSource

class MLTDRepository : RemoteDataSource {

    private val remoteDataSource = MLTDRemoteDataSource

    override fun getAllEvent(callBack: ResponseCallBack<List<EventResponse>>) {
        remoteDataSource.getAllEvent(callBack)
    }
}