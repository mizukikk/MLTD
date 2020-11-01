package com.mizukikk.mltd.main.idol.model

data class FilterIdolData(
    val idolTypeFilterList: List<Int>,
    val centerEffectFilterList: List<Int>,
    val extraTypeFilterList: List<Int>,
    val rarityFilterList: List<Int>,
    val skillFilterList: List<Int>
)