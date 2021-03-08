package com.mizukikk.mltd.data

import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.obj.EventBorders
import com.mizukikk.mltd.api.obj.EventPoint
import com.mizukikk.mltd.api.response.CardResponse
import com.mizukikk.mltd.api.response.EventResponse
import com.mizukikk.mltd.api.response.GetLastPointResponse
import com.mizukikk.mltd.data.source.local.LocalDataSource
import com.mizukikk.mltd.data.source.remote.MLTDRemoteDataSource
import com.mizukikk.mltd.data.source.remote.RemoteDataSource
import com.mizukikk.mltd.room.DBCallBack
import com.mizukikk.mltd.room.entity.IdolEntity
import com.mizukikk.mltd.room.query.IdolItem

class MLTDRepository private constructor(
        private val localDataSource: LocalDataSource,
        private val remoteDataSource: RemoteDataSource
) : RemoteDataSource, LocalDataSource {

    companion object {
        private var INSTANCE: MLTDRepository? = null
        fun newInstance(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource) =
                INSTANCE ?: synchronized(MLTDRemoteDataSource) {
                    MLTDRepository(localDataSource, remoteDataSource)
                }
    }

    //remote start
    override fun downloadAllCard(callBack: ResponseCallBack<List<CardResponse>>) {
        remoteDataSource.downloadAllCard(callBack)
    }

    override fun checkUpdate(lastIdolId: Int, callBack: ResponseCallBack<List<CardResponse>>) {
        remoteDataSource.checkUpdate(lastIdolId, callBack)
    }

    override fun getEventList(callBack: ResponseCallBack<List<EventResponse>>) {
        remoteDataSource.getEventList(callBack)
    }

    override fun getLastEventPoints(eventId: Int, callBack: ResponseCallBack<GetLastPointResponse>) {
        remoteDataSource.getLastEventPoints(eventId, callBack)
    }

    override fun getEventBorders(id: Int, callBack: ResponseCallBack<EventBorders>) {
        remoteDataSource.getEventBorders(id, callBack)
    }

    override fun getEventPoint(id: Int, borders: String, callBack: ResponseCallBack<EventPoint>) {
        remoteDataSource.getEventPoint(id, borders, callBack)
    }

    override fun getEventRankLog(id: Int, type: String, ranks: String, callBack: ResponseCallBack<List<EventPoint>>) {
        remoteDataSource.getEventRankLog(id, type, ranks, callBack)
    }

    override fun getAnivIdolRankLog(id: Int, idolId: Int, callBack: ResponseCallBack<List<EventPoint>>) {
        remoteDataSource.getAnivIdolRankLog(id, idolId, callBack)
    }

    override fun checkDBData(callBack: DBCallBack<List<IdolEntity>>) {
        localDataSource.checkDBData(callBack)
    }
    //remote end

    //local start
    override fun saveAll(count: (progress: Int) -> Unit, vararg cards: CardResponse) {
        localDataSource.saveAll(count, *cards)
    }

    override fun getIdolList(currentId: Int, lang: String, callBack: DBCallBack<List<IdolItem>>) {
        localDataSource.getIdolList(currentId, lang, callBack)
    }

    override fun getAnivIdolIconData(idolId: Int, callBack: DBCallBack<List<IdolItem>>) {
        localDataSource.getAnivIdolIconData(idolId, callBack)
    }

    override fun getAnivEventIdolList(callBack: DBCallBack<List<IdolItem>>) {
        localDataSource.getAnivEventIdolList(callBack)
    }

    //local end
}