package com.mizukikk.mltd.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mizukikk.mltd.room.convert.IdolTypeConvert


@TypeConverters(IdolTypeConvert::class)
@Entity(tableName = "idol")
data class IdolEntity(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int,
    val addDate: String?,
    val awakeningText: String,
    val bonusCostume: BonusCostume?,
    val category: String,
    val centerEffect: CenterEffect?,
    val centerEffectName: String,
    val costume: Costume?,
    val danceMasterBonus: Int,
    val danceMax: Int,
    val danceMaxAwakened: Int,
    val danceMin: Int,
    val danceMinAwakened: Int,
    val extraType: Int,
    val flavorText: String,
    val flavorTextAwakened: String,
    val id: Int,
    val idolId: Int,
    val idolType: Int,
    val levelMax: Int,
    val levelMaxAwakened: Int,
    val life: Int,
    val masterRankMax: Int,
    val name: String,
    val rank5Costume: Rank5Costume?,
    val rarity: Int,
    val resourceId: String,
    val skill: List<Skill>?,
    val skillLevelMax: Int,
    val skillName: String,
    val sortId: Int,
    val visualMasterBonus: Int,
    val visualMax: Int,
    val visualMaxAwakened: Int,
    val visualMin: Int,
    val visualMinAwakened: Int,
    val vocalMasterBonus: Int,
    val vocalMax: Int,
    val vocalMaxAwakened: Int,
    val vocalMin: Int,
    val vocalMinAwakened: Int,
    var lang: String?
)

data class BonusCostume(
    val description: String,
    val id: Int,
    val modelId: String,
    val name: String,
    val resourceId: String,
    val sortId: Int
)

data class CenterEffect(
    val attribute: Int,
    val description: String,
    val id: Int,
    val idolType: Int,
    val value: Int
)

data class Costume(
    val description: String,
    val id: Int,
    val modelId: String,
    val name: String,
    val resourceId: String,
    val sortId: Int
)

data class Rank5Costume(
    val description: String,
    val id: Int,
    val modelId: String,
    val name: String,
    val resourceId: String,
    val sortId: Int
)

data class Skill(
    val description: String,
    val duration: Int,
    val effectId: Int,
    val evaluation: Int,
    val evaluation2: Int,
    val id: Int,
    val interval: Int,
    val probability: Int,
    val value: List<Int>
)