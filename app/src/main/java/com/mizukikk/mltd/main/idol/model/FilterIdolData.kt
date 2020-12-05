package com.mizukikk.mltd.main.idol.model

data class FilterIdolData(
    val idolTypeFilterList: List<Int>,
    val centerEffectFilterList: List<Int>,
    val extraTypeFilterList: List<Int>,
    val rarityFilterList: List<Int>,
    val skillFilterList: List<Int>
)

data class IdolType(val type: String, val value: List<Int>)
data class CenterEffect(val centerEffect: String, val value: List<Int>)
data class ExtraType(val extraType: String, val value: List<Int>)
data class Rarity(val rarity: String, val value: List<Int>)
data class Skill(val skill: String, val value: List<Int>)