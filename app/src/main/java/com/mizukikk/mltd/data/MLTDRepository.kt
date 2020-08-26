package com.mizukikk.mltd.data

import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Event
import com.mizukikk.mltd.data.source.remote.MLTDRemoteDataSource
import com.mizukikk.mltd.data.source.remote.RemoteDataSource

class MLTDRepository private constructor(private val remoteDataSource: RemoteDataSource) :
    RemoteDataSource {

    companion object {
        private var INSTANCE: MLTDRepository? = null
        fun newInstance(remoteDataSource: RemoteDataSource) =
            INSTANCE ?: synchronized(MLTDRemoteDataSource) {
                MLTDRepository(remoteDataSource)
            }
    }

    override fun getAllEvent(callBack: ResponseCallBack<List<Event.EventResponse>>) {
        remoteDataSource.getAllEvent(callBack)
    }
}