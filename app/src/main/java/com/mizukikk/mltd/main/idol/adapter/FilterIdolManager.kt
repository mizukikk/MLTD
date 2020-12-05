package com.mizukikk.mltd.main.idol.adapter

import com.mizukikk.mltd.main.idol.model.*

class FilterIdolManager(
    val centerEffectList: List<CenterEffect>,
    val extraTypeList: List<ExtraType>,
    val idolTypeList: List<IdolType>,
    val rarityList: List<Rarity>,
    val skillList: List<Skill>
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

    val isFilter
        get() = idolTypeAdapter.getFilterList().isNotEmpty()
                || centerEffectAdapter.getFilterList().isNotEmpty()
                || extraTypeAdapter.getFilterList().isNotEmpty()
                || rarityAdapter.getFilterList().isNotEmpty()
                || skillAdapter.getFilterList().isNotEmpty()

    fun showFilterList() {
        idolTypeAdapter.checkFilterBtStatus()
        centerEffectAdapter.checkFilterBtStatus()
        extraTypeAdapter.checkFilterBtStatus()
        rarityAdapter.checkFilterBtStatus()
        skillAdapter.checkFilterBtStatus()
    }

    fun getFilterData() =
        FilterIdolData(
            idolTypeAdapter.getFilterList(),
            centerEffectAdapter.getFilterList(),
            extraTypeAdapter.getFilterList(),
            rarityAdapter.getFilterList(),
            skillAdapter.getFilterList()
        )

    fun clearFilter() {
        idolTypeAdapter.clearFilterList()
        centerEffectAdapter.clearFilterList()
        extraTypeAdapter.clearFilterList()
        rarityAdapter.clearFilterList()
        skillAdapter.clearFilterList()
    }

}