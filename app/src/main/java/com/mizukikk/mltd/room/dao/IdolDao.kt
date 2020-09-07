package com.mizukikk.mltd.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mizukikk.mltd.room.entity.*

@Dao
interface IdolDao {

    @Query("SELECT * FROM idol WHERE idol.id = :id")
    fun searchById(id: Int): List<IdolEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIdol(idolEntity: IdolEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBonusCostume(bonusCostumeEntity: BonusCostumeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCenterEffect(centerEffectEntity: CenterEffectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCostume(costumeEntity: CostumeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRank5Costume(rank5CostumeEntity: Rank5CostumeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSkill(skillEntity: SkillEntity)

}