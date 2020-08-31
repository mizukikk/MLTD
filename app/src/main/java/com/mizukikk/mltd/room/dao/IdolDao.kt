package com.mizukikk.mltd.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mizukikk.mltd.room.entity.IdolEntity

@Dao
interface IdolDao {

    @Query("SELECT * FROM idol WHERE idol.id = :id")
    fun searckById(id: Int): List<IdolEntity>

    @Insert
    fun insert(idol: IdolEntity)

    @Insert
    fun insertAll(vararg idols: IdolEntity)
}