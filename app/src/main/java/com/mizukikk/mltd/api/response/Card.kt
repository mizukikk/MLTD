package com.mizukikk.mltd.api.response

import com.google.gson.annotations.SerializedName

import com.google.gson.annotations.Expose


class Card {

    data class CardResponse(
        @Expose
        @SerializedName("awakeningText")
        val awakeningText: String,
        @Expose
        @SerializedName("category")
        val category: String,
        @Expose
        @SerializedName("centerEffect")
        val centerEffect: CenterEffect,
        @Expose
        @SerializedName("centerEffectName")
        val centerEffectName: String,
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
        @SerializedName("rarity")
        val rarity: Int,
        @Expose
        @SerializedName("resourceId")
        val resourceId: String,
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
        val vocalMinAwakened: Int
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
        @SerializedName("idolType")
        val idolType: Int,
        @Expose
        @SerializedName("value")
        val value: Int
    )


}