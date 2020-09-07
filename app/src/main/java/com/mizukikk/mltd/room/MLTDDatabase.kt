package com.mizukikk.mltd.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.data.source.local.LocalDataSource
import com.mizukikk.mltd.room.dao.IdolDao
import com.mizukikk.mltd.room.entity.*


@Database(
    entities = [IdolEntity::class,
        BonusCostumeEntity::class,
        CenterEffectEntity::class,
        CostumeEntity::class,
        Rank5CostumeEntity::class,
        SkillEntity::class],
    version = 1
)
abstract class MLTDDatabase : RoomDatabase() {
    companion object {
        private const val MLTD_DB_NAME = "mltd.db"
        private var INSTANCE: MLTDDatabase? = null
        fun getInstance() =
            synchronized(MLTDDatabase::class) {
                INSTANCE ?: Room.databaseBuilder(
                    MLTDApplication.applicationContext,
                    MLTDDatabase::class.java,
                    MLTD_DB_NAME
                ).build()
            }
    }

    abstract fun IdolDao(): IdolDao
}