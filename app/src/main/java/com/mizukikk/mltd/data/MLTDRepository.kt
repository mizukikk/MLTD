package com.mizukikk.mltd.data

import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Event
import com.mizukikk.mltd.data.source.remote.MLTDRemoteDataSource
import com.mizukikk.mltd.data.source.remote.RemoteDataSource

class MLTDRepository : RemoteDataSource {

    private val remoteDataSource = MLTDRemoteDataSource

    override fun getAllEvent(callBack: ResponseCallBack<List<Event.EventResponse>>) {
        remoteDataSource.getAllEvent(callBack)
    }
}