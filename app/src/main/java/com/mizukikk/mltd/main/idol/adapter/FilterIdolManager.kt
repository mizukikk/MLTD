package com.mizukikk.mltd.main.idol.adapter

import com.mizukikk.mltd.main.idol.model.*

class FilterIdolManager(
    val centerEffectList: List<CenterEffect>,
    val extraTypeList: List<ExtraType>,
    val idolTypeList: List<IdolType>,
    val rarityList: List<Rarity>,
    val skillList: List<Skill>,
    val skillDuration :List<Int> = mutableListOf(7,8,9,10,11,12,13,14)
) {

    val idolTypeAdapter by lazy {
        FilterIdolAdapter(
            idolTypeList,
            FilterIdolAdapter.FILTER_TYPE_IDOL_TYPE
        )
    }
    val centerEffectAdapter by lazy {
        FilterIdolAdapter(
            centerEffectList,
            FilterIdolAdapter.FILTER_TYPE_CENTER_EFFECT
        )
    }
    val extraTypeAdapter by lazy {
        FilterIdolAdapter(
            extraTypeList,
            FilterIdolAdapter.FILTER_TYPE_EXTRA_TYPE
        )
    }
    val rarityAdapter by lazy {
        FilterIdolAdapter(
            rarityList,
            FilterIdolAdapter.FILTER_TYPE_RARITY
        )
    }
    val skillAdapter by lazy { FilterIdolAdapter(skillList, FilterIdolAdapter.FILTER_TYPE_SKILL) }

    val skillDurationAdapter by lazy { FilterIdolAdapter(skillDuration, FilterIdolAdapter.FILTER_TYPE_SKILL_DURATION) }

    val isFilter
        get() = idolTypeAdapter.getFilterList().isNotEmpty()
                || centerEffectAdapter.getFilterList().isNotEmpty()
                || extraTypeAdapter.getFilterList().isNotEmpty()
                || rarityAdapter.getFilterList().isNotEmpty()
                || skillAdapter.getFilterList().isNotEmpty()
                || skillDurationAdapter.getFilterList().isNotEmpty()

    fun showFilterList() {
        idolTypeAdapter.checkFilterBtStatus()
        centerEffectAdapter.checkFilterBtStatus()
        extraTypeAdapter.checkFilterBtStatus()
        rarityAdapter.checkFilterBtStatus()
        skillAdapter.checkFilterBtStatus()
        skillDurationAdapter.checkFilterBtStatus()
    }

    fun getFilterData() =
        FilterIdolData(
            idolTypeAdapter.getFilterList(),
            centerEffectAdapter.getFilterList(),
            extraTypeAdapter.getFilterList(),
            rarityAdapter.getFilterList(),
            skillAdapter.getFilterList(),
            skillDurationAdapter.getFilterList()
        )

    fun clearFilter() {
        idolTypeAdapter.clearFilterList()
        centerEffectAdapter.clearFilterList()
        extraTypeAdapter.clearFilterList()
        rarityAdapter.clearFilterList()
        skillAdapter.clearFilterList()
        skillDurationAdapter.clearFilterList()
    }

}