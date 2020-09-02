package com.mizukikk.mltd.room.entity

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.room.convert.IdolTypeConvert


@TypeConverters(IdolTypeConvert::class)
@Entity(tableName = "idol")
data class IdolEntity(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int,
    @ColumnInfo(name = "addDate")
    val addDate: String?,
    @ColumnInfo(name = "awakeningText")
    val awakeningText: String,
    @ColumnInfo(name = "bonusCostume")
    val bonusCostume: BonusCostume?,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "centerEffect")
    val centerEffect: CenterEffect?,
    @ColumnInfo(name = "centerEffectName")
    val centerEffectName: String,
    @ColumnInfo(name = "costume")
    val costume: Costume?,
    @ColumnInfo(name = "danceMasterBonus")
    val danceMasterBonus: Int,
    @ColumnInfo(name = "danceMax")
    val danceMax: Int,
    @ColumnInfo(name = "danceMaxAwakened")
    val danceMaxAwakened: Int,
    @ColumnInfo(name = "danceMin")
    val danceMin: Int,
    @ColumnInfo(name = "danceMinAwakened")
    val danceMinAwakened: Int,
    @ColumnInfo(name = "extraType")
    val extraType: Int,
    @ColumnInfo(name = "flavorText")
    val flavorText: String,
    @ColumnInfo(name = "flavorTextAwakened")
    val flavorTextAwakened: String,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "idolId")
    val idolId: Int,
    @ColumnInfo(name = "idolType")
    val idolType: Int,
    @ColumnInfo(name = "levelMax")
    val levelMax: Int,
    @ColumnInfo(name = "levelMaxAwakened")
    val levelMaxAwakened: Int,
    @ColumnInfo(name = "life")
    val life: Int,
    @ColumnInfo(name = "masterRankMax")
    val masterRankMax: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "rank5Costume")
    val rank5Costume: Rank5Costume?,
    @ColumnInfo(name = "rarity")
    val rarity: Int,
    @ColumnInfo(name = "resourceId")
    val resourceId: String,
    @ColumnInfo(name = "skill")
    val skill: List<Skill>?,
    @ColumnInfo(name = "skillLevelMax")
    val skillLevelMax: Int,
    @ColumnInfo(name = "skillName")
    val skillName: String,
    @ColumnInfo(name = "sortId")
    val sortId: Int,
    @ColumnInfo(name = "visualMasterBonus")
    val visualMasterBonus: Int,
    @ColumnInfo(name = "visualMax")
    val visualMax: Int,
    @ColumnInfo(name = "visualMaxAwakened")
    val visualMaxAwakened: Int,
    @ColumnInfo(name = "visualMin")
    val visualMin: Int,
    @ColumnInfo(name = "visualMinAwakened")
    val visualMinAwakened: Int,
    @ColumnInfo(name = "vocalMasterBonus")
    val vocalMasterBonus: Int,
    @ColumnInfo(name = "vocalMax")
    val vocalMax: Int,
    @ColumnInfo(name = "vocalMaxAwakened")
    val vocalMaxAwakened: Int,
    @ColumnInfo(name = "vocalMin")
    val vocalMin: Int,
    @ColumnInfo(name = "vocalMinAwakened")
    val vocalMinAwakened: Int,
    @ColumnInfo(name = "lang")
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