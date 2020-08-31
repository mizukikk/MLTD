package com.mizukikk.mltd

import com.mizukikk.mltd.data.MLTDRepository
import com.mizukikk.mltd.data.source.local.MLTDLocalDataSource
import com.mizukikk.mltd.data.source.remote.MLTDRemoteDataSource
import com.mizukikk.mltd.room.DBExecutor
import com.mizukikk.mltd.room.MLTDDatabase

object Inject {

    fun providerMLTDRepository(): MLTDRepository {
        val mltdDB = MLTDDatabase.getInstance()
        val localDataSource =
            MLTDLocalDataSource.getInstance(
                DBExecutor(),
                mltdDB.IdolDao()
            )
        return MLTDRepository.newInstance(
            localDataSource,
            MLTDRemoteDataSource
        )
    }

}