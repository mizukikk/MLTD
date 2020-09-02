package com.mizukikk.mltd.data.source.local

import android.util.Log
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.room.DBExecutor
import com.mizukikk.mltd.room.dao.IdolDao
import com.mizukikk.mltd.room.entity.IdolEntity

class MLTDLocalDataSource private constructor(
    private val dbExecutor: DBExecutor,
    private val idolDao: IdolDao
) : LocalDataSource {
    companion object {
        private val TAG = MLTDLocalDataSource::class.java.simpleName
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(
            dbExecutor: DBExecutor,
            idolDao: IdolDao
        ) =
            synchronized(LocalDataSource::class.java) {
                INSTANCE ?: MLTDLocalDataSource(dbExecutor, idolDao)
            }
    }

    override fun saveAll(count: () -> Unit, vararg idols: IdolEntity) {
        dbExecutor.dbIOThread.execute {
            idols.forEach { entity ->
                if (idolDao.searckById(entity.id).isEmpty()) {
                    if (entity.lang == null || entity.lang!!.isEmpty())
                        entity.lang = PreferencesHelper.apiLanguage
                    idolDao.insert(entity)
                }
            }
        }
    }
}