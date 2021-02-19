package com.mizukikk.mltd.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.room.entity.*
import com.mizukikk.mltd.room.query.IdolItem

@Dao
interface IdolDao {

    @Query("SELECT * FROM idol WHERE idol.lang = :lang AND idol.cardId < 8000 ORDER BY idol.cardId DESC LIMIT 1")
    fun checkDBData(lang: String = PreferencesHelper.apiLanguage): List<IdolEntity>

    @Query("SELECT * FROM idol WHERE idol.cardId = :id")
    fun searchById(id: Int): List<IdolEntity>

    @Query("SELECT * FROM idol WHERE idol.lang = :lang ORDER BY idol.cardId DESC ")
    fun getFirstIdolList(lang: String): List<IdolItem>

    @Query("SELECT * FROM idol WHERE idol.cardId <= :currentId AND idol.lang = :lang ORDER BY idol.cardId DESC LIMIT 20")
    fun getIdolList(currentId: Int, lang: String): List<IdolItem>

    @Query("SELECT * FROM idol WHERE idol.lang = :lang ORDER BY idol.cardId DESC")
    fun getAllIdolList(lang: String): List<IdolItem>

    @Query("SELECT * FROM idol WHERE idol.lang = 'ja' AND idol.rarity = 4 AND idol.idolId = :idolId ORDER BY idol.cardId DESC LIMIT 1 ")
    fun getAnivIdolIconData(idolId: Int): List<IdolItem>

    @Query("SELECT * FROM idol WHERE idol.lang = 'ja' AND idol.rarity = 4 AND category != 'event3' GROUP BY idol.idolId ORDER BY idolId DESC")
    fun getAnivEventIdolList(): List<IdolItem>

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