package com.mizukikk.mltd.data.source.local

import com.mizukikk.mltd.api.response.CardResponse
import com.mizukikk.mltd.room.DBCallBack
import com.mizukikk.mltd.room.entity.IdolEntity
import com.mizukikk.mltd.room.query.IdolItem

interface LocalDataSource {
    fun checkDBData(callBack: DBCallBack<List<IdolEntity>>)
    fun saveAll(count: (progress: Int) -> Unit, vararg cards: CardResponse)
    fun getIdolList(currentId: Int, lang: String, callBack: DBCallBack<List<IdolItem>>)
    fun getAnivIdolIconData(idolId: Int, callBack: DBCallBack<List<IdolItem>>)
    fun getAnivEventIdolList(callBack: DBCallBack<List<IdolItem>>)
}