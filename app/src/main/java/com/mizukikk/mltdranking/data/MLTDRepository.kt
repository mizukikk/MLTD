package com.mizukikk.mltdranking.data

import com.mizukikk.mltdranking.api.ResponseCallBack
import com.mizukikk.mltdranking.api.response.EventResponse
import com.mizukikk.mltdranking.data.remote.MLTDRemoteDataSource
import com.mizukikk.mltdranking.data.remote.RemoteDataSource

class MLTDRepository : RemoteDataSource {

    private val remoteDataSource = MLTDRemoteDataSource

    override fun getAllEvent(callBack: ResponseCallBack<List<EventResponse>>) {
        remoteDataSource.getAllEvent(callBack)
    }
}