package com.mizukikk.mltd.api.response

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName

import com.google.gson.annotations.Expose
import com.google.gson.reflect.TypeToken
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.room.entity.*
import com.mizukikk.mltd.utils.GsonUtils


class Card {

    data class CardResponse(
        @Expose
        @SerializedName("addDate")
        val addDate: String,
        @Expose
        @SerializedName("awakeningText")
        val awakeningText: String,
        @Expose
        @SerializedName("bonusCostume")
        val bonusCostume: BonusCostume?,
        @Expose
        @SerializedName("category")
        val category: String,
        @Expose
        @SerializedName("centerEffect")
        val centerEffect: CenterEffect?,
        @Expose
        @SerializedName("centerEffectName")
        val centerEffectName: String,
        @Expose
        @SerializedName("costume")
        val costume: Costume?,
        @Expose
        @SerializedName("danceMasterBonus")
        val danceMasterBonus: Int,
        @Expose
        @SerializedName("danceMax")
        val danceMax: Int,
        @Expose
        @SerializedName("danceMaxAwakened")
        val danceMaxAwakened: Int,
        @Expose
        @SerializedName("danceMin")
        val danceMin: Int,
        @Expose
        @SerializedName("danceMinAwakened")
        val danceMinAwakened: Int,
        @Expose
        @SerializedName("extraType")
        val extraType: Int,
        @Expose
        @SerializedName("flavorText")
        val flavorText: String,
        @Expose
        @SerializedName("flavorTextAwakened")
        val flavorTextAwakened: String,
        @Expose
        @SerializedName("id")
        val id: Int,
        @Expose
        @SerializedName("idolId")
        val idolId: Int,
        @Expose
        @SerializedName("idolType")
        val idolType: Int,
        @Expose
        @SerializedName("levelMax")
        val levelMax: Int,
        @Expose
        @SerializedName("levelMaxAwakened")
        val levelMaxAwakened: Int,
        @Expose
        @SerializedName("life")
        val life: Int,
        @Expose
        @SerializedName("masterRankMax")
        val masterRankMax: Int,
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("rank5Costume")
        val rank5Costume: Rank5Costume?,
        @Expose
        @SerializedName("rarity")
        val rarity: Int,
        @Expose
        @SerializedName("resourceId")
        val resourceId: String,
        @Expose
        @SerializedName("skill")
        val skill: List<Skill>?,
        @Expose
        @SerializedName("skillLevelMax")
        val skillLevelMax: Int,
        @Expose
        @SerializedName("skillName")
        val skillName: String,
        @Expose
        @SerializedName("sortId")
        val sortId: Int,
        @Expose
        @SerializedName("visualMasterBonus")
        val visualMasterBonus: Int,
        @Expose
        @SerializedName("visualMax")
        val visualMax: Int,
        @Expose
        @SerializedName("visualMaxAwakened")
        val visualMaxAwakened: Int,
        @Expose
        @SerializedName("visualMin")
        val visualMin: Int,
        @Expose
        @SerializedName("visualMinAwakened")
        val visualMinAwakened: Int,
        @Expose
        @SerializedName("vocalMasterBonus")
        val vocalMasterBonus: Int,
        @Expose
        @SerializedName("vocalMax")
        val vocalMax: Int,
        @Expose
        @SerializedName("vocalMaxAwakened")
        val vocalMaxAwakened: Int,
        @Expose
        @SerializedName("vocalMin")
        val vocalMin: Int,
        @Expose
        @SerializedName("vocalMinAwakened")
        val vocalMinAwakened: Int,
        var lang: String?
    ) {

        val toIdolEntity
            get() = try {
                val json = GsonUtils.toJsonString(this)
                val idolEntity = GsonUtils.toDataObj(json, IdolEntity::class.java)
                idolEntity?.bonusCostumeId = bonusCostume?.id
                idolEntity?.centerEffectId = centerEffect?.id
                idolEntity?.costumeId = costume?.id
                idolEntity?.rank5CostumeId = rank5Costume?.id
                val skillIds = skill?.map { it.id }
                idolEntity?.skillId = skillIds?.get(0) ?: null
                idolEntity?.lang = lang ?: PreferencesHelper.apiLanguage
                idolEntity
            } catch (e: JsonSyntaxException) {
                null
            }
        val toBonusCostumeEntity
            get() = try {
                if (bonusCostume != null) {
                    val json = GsonUtils.toJsonString(bonusCostume)
                    val entity = GsonUtils.toDataObj(json, BonusCostumeEntity::class.java)
                    entity?.lang = lang ?: PreferencesHelper.apiLanguage
                    entity
                } else {
                    null
                }
            } catch (e: JsonSyntaxException) {
                null
            }
        val toCenterEffectEntity
            get() = try {
                if (centerEffect != null) {
                    val json = GsonUtils.toJsonString(centerEffect)
                    val entity = GsonUtils.toDataObj(json, CenterEffectEntity::class.java)
                    entity?.lang = lang ?: PreferencesHelper.apiLanguage
                    entity
                } else {
                    null
                }
            } catch (e: JsonSyntaxException) {
                null
            }
        val toCostumeEntity
            get() = try {
                if (costume != null) {
                    val json = GsonUtils.toJsonString(costume)
                    val entity = GsonUtils.toDataObj(json, CostumeEntity::class.java)
                    entity?.lang = lang ?: PreferencesHelper.apiLanguage
                    entity
                } else {
                    null
                }
            } catch (e: JsonSyntaxException) {
                null
            }

        val toRank5CostumeEntity
            get() = try {
                if (rank5Costume != null) {
                    val json = GsonUtils.toJsonString(rank5Costume)
                    val entity = GsonUtils.toDataObj(json, Rank5CostumeEntity::class.java)
                    entity?.lang = lang ?: PreferencesHelper.apiLanguage
                    entity
                } else {
                    null
                }
            } catch (e: JsonSyntaxException) {
                null
            }
        val toSkillEntity
            get() = try {
                if (skill != null) {
                    val json = GsonUtils.toJsonString(skill)
                    val entityList = Gson().fromJson<List<SkillEntity>>(
                        json,
                        object : TypeToken<List<SkillEntity>>() {}.type
                    )
                    entityList.forEach {
                        it.lang = lang ?: PreferencesHelper.apiLanguage
                    }
                    entityList
                } else {
                    null
                }
            } catch (e: JsonSyntaxException) {
                null
            }
    }

    data class BonusCostume(
        @Expose
        @SerializedName("description")
        val description: String,
        @Expose
        @SerializedName("id")
        val id: Int,
        @Expose
        @SerializedName("modelId")
        val modelId: String,
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("resourceId")
        val resourceId: String,
        @Expose
        @SerializedName("sortId")
        val sortId: Int
    )

    data class CenterEffect(
        @Expose
        @SerializedName("attribute")
        val attribute: Int,
        @Expose
        @SerializedName("description")
        val description: String,
        @Expose
        @SerializedName("id")
        val id: Int,
        @Expose
        @SerializedName("idolType.txt")
        val idolType: Int,
        @Expose
        @SerializedName("value")
        val value: Int
    )

    data class Costume(
        @Expose
        @SerializedName("description")
        val description: String,
        @Expose
        @SerializedName("id")
        val id: Int,
        @Expose
        @SerializedName("modelId")
        val modelId: String,
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("resourceId")
        val resourceId: String,
        @Expose
        @SerializedName("sortId")
        val sortId: Int
    )

    data class Rank5Costume(
        @Expose
        @SerializedName("description")
        val description: String,
        @Expose
        @SerializedName("id")
        val id: Int,
        @Expose
        @SerializedName("modelId")
        val modelId: String,
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("resourceId")
        val resourceId: String,
        @Expose
        @SerializedName("sortId")
        val sortId: Int
    )

    data class Skill(
        @Expose
        @SerializedName("description")
        val description: String,
        @Expose
        @SerializedName("duration")
        val duration: Int,
        @Expose
        @SerializedName("effectId")
        val effectId: Int,
        @Expose
        @SerializedName("evaluation")
        val evaluation: Int,
        @Expose
        @SerializedName("evaluation2")
        val evaluation2: Int,
        @Expose
        @SerializedName("id")
        val id: Int,
        @Expose
        @SerializedName("interval")
        val interval: Int,
        @Expose
        @SerializedName("probability")
        val probability: Int,
        @Expose
        @SerializedName("value")
        val value: List<Int>
    )


}