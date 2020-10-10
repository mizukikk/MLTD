package com.mizukikk.mltd.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mizukikk.mltd.data.model.IdolField
import com.mizukikk.mltd.room.convert.IdolTypeConvert
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Field


@Parcelize
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
    var skillId: Int?,
    var lang: String?
) : Parcelable {
    val cardBGUrl
        get() = if (rarity == IdolField.Rarity.SSR)
            IdolField.Card.CARD_BG.format("${resourceId}_0")
        else
            IdolField.Card.CARD.format("${resourceId}_0_b")
    val cardWeekBGUrl
        get() = if (rarity == IdolField.Rarity.SSR)
            IdolField.Card.CARD_BG.format("${resourceId}_1")
        else
            IdolField.Card.CARD.format("${resourceId}_1_b")
}

@Parcelize
@Entity(tableName = "centerEffect")
data class CenterEffectEntity(
    val attribute: Int,
    val description: String,
    @PrimaryKey
    @ColumnInfo(name = "centerEffectId")
    val id: Int,
    val idolType: Int,
    val value: Int
) : Parcelable

@Parcelize
@TypeConverters(IdolTypeConvert::class)
@Entity(tableName = "skill")
data class SkillEntity(
    val description: String,
    val duration: Int,
    val effectId: Int,
    val evaluation: Int,
    val evaluation2: Int,
    @PrimaryKey
    @ColumnInfo(name = "skillId")
    val id: Int,
    val interval: Int,
    val probability: Int,
    val value: List<Int>
) : Parcelable

@Parcelize
@Entity(tableName = "costume")
data class CostumeEntity(
    val description: String,
    @PrimaryKey
    @ColumnInfo(name = "costumeId")
    val id: Int,
    val modelId: String,
    val name: String,
    @ColumnInfo(name = "costumeResId")
    val resourceId: String,
    val sortId: Int
) : Parcelable

@Parcelize
@Entity(tableName = "bonusCostume")
data class BonusCostumeEntity(
    val description: String,
    @PrimaryKey
    @ColumnInfo(name = "bonusCostumeId")
    val id: Int,
    val modelId: String,
    val name: String,
    @ColumnInfo(name = "bonusCostumeResId")
    val resourceId: String,
    val sortId: Int
) : Parcelable

@Parcelize
@Entity(tableName = "rank5Costume")
data class Rank5CostumeEntity(
    val description: String,
    @PrimaryKey
    @ColumnInfo(name = "rank5CostumeId")
    val id: Int,
    val modelId: String,
    val name: String,
    @ColumnInfo(name = "rank5CostumeResId")
    val resourceId: String,
    val sortId: Int
) : Parcelable