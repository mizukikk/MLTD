package com.mizukikk.mltd.main.idol.model

import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper

data class FilterIdolData(
    val idolTypeFilterList: List<Int>,
    val centerEffectFilterList: List<Int>,
    val extraTypeFilterList: List<Int>,
    val rarityFilterList: List<Int>,
    val skillFilterList: List<Int>
)

data class IdolType(val type: String, val value: List<Int>)

data class CenterEffect(val centerEffect: Map<String, String>, val value: List<Int>) {
    val centerEffectValue get() = centerEffect[PreferencesHelper.apiLanguage]
}

data class ExtraType(val extraType: Map<String, String>, val value: List<Int>) {
    val extraTypeValue get() = extraType[PreferencesHelper.apiLanguage]
}

data class Rarity(val rarity: String, val value: List<Int>)
data class Skill(val skill: Map<String, String?>, val value: List<Int>) {
    val skillValue get() = skill[PreferencesHelper.apiLanguage]
}