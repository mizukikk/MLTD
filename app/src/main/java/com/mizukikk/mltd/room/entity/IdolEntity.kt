package com.mizukikk.mltd.room.entity

import androidx.room.ColumnInfo
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
    val category: String,
    val centerEffectName: String,
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
    val rarity: Int,
    @ColumnInfo(name = "IdolResId")
    val resourceId: String,
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
    var bonusCostumeId: Int?,
    var centerEffectId: Int?,
    var costumeId: Int?,
    var rank5CostumeId: Int?,
    var skillIds: List<Int>?,
    var lang: String?
)

@Entity(tableName = "bonusCostume")
data class BonusCostumeEntity(
    val description: String,
    @PrimaryKey
    val id: Int,
    val modelId: String,
    val name: String,
    @ColumnInfo(name = "bonusCostumeResId")
    val resourceId: String,
    val sortId: Int
)

@Entity(tableName = "centerEffect")
data class CenterEffectEntity(
    val attribute: Int,
    val description: String,
    @PrimaryKey
    val id: Int,
    val idolType: Int,
    val value: Int
)

@Entity(tableName = "costume")
data class CostumeEntity(
    val description: String,
    @PrimaryKey
    val id: Int,
    val modelId: String,
    val name: String,
    @ColumnInfo(name = "costumeResId")
    val resourceId: String,
    val sortId: Int
)

@Entity(tableName = "rank5Costume")
data class Rank5CostumeEntity(
    val description: String,
    @PrimaryKey
    val id: Int,
    val modelId: String,
    val name: String,
    @ColumnInfo(name = "rank5CostumeResId")
    val resourceId: String,
    val sortId: Int
)

@TypeConverters(IdolTypeConvert::class)
@Entity(tableName = "skill")
data class SkillEntity(
    val description: String,
    val duration: Int,
    val effectId: Int,
    val evaluation: Int,
    val evaluation2: Int,
    @PrimaryKey
    val id: Int,
    val interval: Int,
    val probability: Int,
    val value: List<Int>
)
