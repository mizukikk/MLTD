package com.mizukikk.mltd.room.dao

import androidx.room.*
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.room.entity.*
import com.mizukikk.mltd.room.query.IdolItem

@Dao
interface IdolDao {

    @Query("SELECT * FROM idol WHERE idol.lang = :lang LIMIT 1")
    fun checkDBData(lang: String = PreferencesHelper.apiLanguage): List<IdolEntity>

    @Query("SELECT * FROM idol WHERE idol.id = :id")
    fun searchById(id: Int): List<IdolEntity>

    @Query("SELECT * FROM idol WHERE idol.id > :currentId AND idol.lang = :lang LIMIT 20")
    fun getIdolList(currentId: Int, lang: String): List<IdolItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIdol(idolEntity: IdolEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCenterEffect(centerEffectEntity: CenterEffectEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCostume(costumeEntity: CostumeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBonusCostume(bonusCostumeEntity: BonusCostumeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRank5Costume(rank5CostumeEntity: Rank5CostumeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSkill(skillEntity: SkillEntity)

}