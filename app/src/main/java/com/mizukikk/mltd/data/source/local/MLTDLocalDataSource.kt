package com.mizukikk.mltd.data.source.local

import android.util.Log
import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.room.DBCallBack
import com.mizukikk.mltd.room.DBExecutor
import com.mizukikk.mltd.room.dao.IdolDao
import com.mizukikk.mltd.room.entity.IdolEntity
import com.mizukikk.mltd.room.query.IdolItem
import java.lang.Exception

class MLTDLocalDataSource private constructor(
    private val dbExecutor: DBExecutor,
    private val idolDao: IdolDao
) : LocalDataSource {
    companion object {
        private val TAG = MLTDLocalDataSource::class.java.simpleName
        private var INSTANCE: LocalDataSource? = null
        private val lock = Any()
        fun getInstance(
            dbExecutor: DBExecutor,
            idolDao: IdolDao
        ) =
            synchronized(LocalDataSource::class.java) {
                INSTANCE ?: MLTDLocalDataSource(dbExecutor, idolDao)
            }
    }

    override fun checkDBData(callBack: DBCallBack<List<IdolEntity>>) {
        dbExecutor.dbIOThread.execute {
            try {
                val idolList = idolDao.checkDBData()
                callBack.success(idolList)
            } catch (e: Exception) {
                callBack.fail()
            }

        }
    }

    override fun saveAll(count: (progress: Int) -> Unit, vararg cards: Card.CardResponse) {
        dbExecutor.dbIOThread.execute {
            var progress = 0
            cards.forEach { card ->
                card.toIdolEntity?.let { entity ->
                    idolDao.insertIdol(entity)
                }
                card.toBonusCostumeEntity?.let { entity ->
                    idolDao.insertBonusCostume(entity)
                }
                card.toCenterEffectEntity?.let { entity ->
                    idolDao.insertCenterEffect(entity)
                }
                card.toCostumeEntity?.let { entity ->
                    idolDao.insertCostume(entity)
                }
                card.toRank5CostumeEntity?.let { entity ->
                    idolDao.insertRank5Costume(entity)
                }
                card.toSkillEntity?.let { skillList ->
                    skillList.forEach { entity ->
                        idolDao.insertSkill(entity)
                    }
                }
                progress++
                count.invoke(progress)
            }
        }
    }

    override fun getIdolList(
        currentId: Int,
        lang: String,
        callBack: DBCallBack<List<IdolItem>>
    ) {
        dbExecutor.dbIOThread.execute {
            try {
//                val idolList =
//                    if (currentId >= 0)
//                        idolDao.getIdolList(currentId, lang)
//                    else
//                        idolDao.getFirstIdolList(lang)
                val idolList = idolDao.getAllIdolList(lang)
                callBack.success(idolList)
            } catch (e: Exception) {
                callBack.fail()
            }
        }
    }
}