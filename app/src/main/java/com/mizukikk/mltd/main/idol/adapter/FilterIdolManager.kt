package com.mizukikk.mltd.main.idol.adapter

import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.R

class FilterIdolManager {
    val idolTypeAdapter: FilterIdolAdapter
    val centerEffectAdapter: FilterIdolAdapter
    val extraTypeAdapter: FilterIdolAdapter
    val rarityAdapter: FilterIdolAdapter
    val skillAdapter: FilterIdolAdapter

    init {
        val idolTypeArray =
            MLTDApplication.applicationContext.resources.getStringArray(R.array.idolType)
        val rarityArray =
            MLTDApplication.applicationContext.resources.getStringArray(R.array.rarity)
        val extraTypeArray =
            MLTDApplication.applicationContext.resources.getStringArray(R.array.extraType)
        val skillEffectArray =
            MLTDApplication.applicationContext.resources.getStringArray(R.array.skillEffect)
        val centerEffectAttributeArray =
            MLTDApplication.applicationContext.resources.getStringArray(R.array.centerEffectAttribute)
        idolTypeAdapter =
            FilterIdolAdapter(idolTypeArray, FilterIdolAdapter.FILTER_TYPE_IDOL_TYPE)
        centerEffectAdapter = FilterIdolAdapter(
            centerEffectAttributeArray,
            FilterIdolAdapter.FILTER_TYPE_CENTER_EFFECT
        )
        extraTypeAdapter =
            FilterIdolAdapter(extraTypeArray, FilterIdolAdapter.FILTER_TYPE_EXTRA_TYPE)
        rarityAdapter =
            FilterIdolAdapter(rarityArray, FilterIdolAdapter.FILTER_TYPE_RARITY)
        skillAdapter =
            FilterIdolAdapter(skillEffectArray, FilterIdolAdapter.FILTER_TYPE_SKILL)
    }

    fun showFilterList() {
        idolTypeAdapter.checkFilterBtStatus()
        centerEffectAdapter.checkFilterBtStatus()
        extraTypeAdapter.checkFilterBtStatus()
        rarityAdapter.checkFilterBtStatus()
        skillAdapter.checkFilterBtStatus()
    }

    fun getFilterList() {
        idolTypeAdapter.getFilterList()
        centerEffectAdapter.getFilterList()
        extraTypeAdapter.getFilterList()
        rarityAdapter.getFilterList()
        skillAdapter.getFilterList()
    }
}