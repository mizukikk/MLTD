package com.mizukikk.mltd.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.mizukikk.mltd.data.Field
import com.mizukikk.mltd.data.model.IdolField
import com.mizukikk.mltd.room.convert.IdolTypeConvert
import kotlinx.android.parcel.Parcelize


@Parcelize
@TypeConverters(IdolTypeConvert::class)
@Entity(tableName = "idol",
    primaryKeys = ["cardId", "lang"]
)
data class IdolEntity(
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
    @ColumnInfo(name = "cardId")
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
    var lang: String = Field.API.LANG_JP
) : Parcelable {
   val idolName get() =  run {
        val nameSplitList = name.split("　")
        if (nameSplitList.size == 2) {
            nameSplitList[1]
        } else {
            name
        }
    }
    val cardBGUrl
        get() = when (extraType) {
            IdolField.ExtraType.ANVI_1,
            IdolField.ExtraType.ANVI_2,
            IdolField.ExtraType.ANVI_3 -> IdolField.URL.CARD_FORMAT.format("${resourceId}_0_b")
            else -> IdolField.URL.CARD_BG_FORMAT.format("${resourceId}_0")
        }
    val cardBGAwakenedUrl
        get() = when (extraType) {
            IdolField.ExtraType.ANVI_1,
            IdolField.ExtraType.ANVI_2,
            IdolField.ExtraType.ANVI_3 -> IdolField.URL.CARD_FORMAT.format("${resourceId}_1_b")
            else -> IdolField.URL.CARD_BG_FORMAT.format("${resourceId}_1")
        }

    val cardWithSignedUrl
        get() =
            IdolField.URL.CARD_FORMAT.format("${resourceId}_0_a")

    val cardWithSignedAwakenedUrl
        get() =
            IdolField.URL.CARD_FORMAT.format("${resourceId}_1_a")

    val cardUrl
        get() =
            IdolField.URL.CARD_FORMAT.format("${resourceId}_0_b")

    val cardAwakenedUrl
        get() =
            IdolField.URL.CARD_FORMAT.format("${resourceId}_1_b")

    val iconUrl get() = IdolField.URL.ICON_FORMAT.format("${resourceId}_0")

    val iconAwakenedUrl get() = IdolField.URL.ICON_FORMAT.format("${resourceId}_1")

}

@Parcelize
@Entity(tableName = "centerEffect",
    primaryKeys = ["centerEffectId", "lang"])
data class CenterEffectEntity(
    val attribute: Int,
    val description: String,
    @ColumnInfo(name = "centerEffectId")
    val id: Int,
    val idolType: Int,
    val value: Int,
    var lang: String = Field.API.LANG_JP
) : Parcelable

@Parcelize
@TypeConverters(IdolTypeConvert::class)
@Entity(tableName = "skill",
    primaryKeys = ["skillId", "lang"])
data class SkillEntity(
    val description: String,
    val duration: Int,
    val effectId: Int,
    val evaluation: Int,
    val evaluation2: Int,
    @ColumnInfo(name = "skillId")
    val id: Int,
    val interval: Int,
    val probability: Int,
    val value: List<Int>,
    var lang: String = Field.API.LANG_JP
) : Parcelable

@Parcelize
@Entity(tableName = "costume",
    primaryKeys = ["costumeId", "lang"])
data class CostumeEntity(
    val description: String,
    @ColumnInfo(name = "costumeId")
    val id: Int,
    val modelId: String,
    val name: String,
    @ColumnInfo(name = "costumeResId")
    val resourceId: String,
    val sortId: Int,
    var lang: String = Field.API.LANG_JP
) : Parcelable {
    val costumeUrl get() = IdolField.URL.COSTUME_FORMAT.format(resourceId)
}

@Parcelize
@Entity(tableName = "bonusCostume",
    primaryKeys = ["bonusCostumeId", "lang"])
data class BonusCostumeEntity(
    val description: String,
    @ColumnInfo(name = "bonusCostumeId")
    val id: Int,
    val modelId: String,
    val name: String,
    @ColumnInfo(name = "bonusCostumeResId")
    val resourceId: String,
    val sortId: Int,
    var lang: String = Field.API.LANG_JP
) : Parcelable {
    val costumeUrl get() = IdolField.URL.COSTUME_FORMAT.format(resourceId)
}

@Parcelize
@Entity(tableName = "rank5Costume",
    primaryKeys = ["rank5CostumeId", "lang"])
data class Rank5CostumeEntity(
    val description: String,
    @ColumnInfo(name = "rank5CostumeId")
    val id: Int,
    val modelId: String,
    val name: String,
    @ColumnInfo(name = "rank5CostumeResId")
    val resourceId: String,
    val sortId: Int,
    var lang: String = Field.API.LANG_JP
) : Parcelable {
    val costumeUrl get() = IdolField.URL.COSTUME_FORMAT.format(resourceId)
}