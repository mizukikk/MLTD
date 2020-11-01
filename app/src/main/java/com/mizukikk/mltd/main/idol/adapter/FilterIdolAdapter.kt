package com.mizukikk.mltd.main.idol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mizukikk.mltd.R
import com.mizukikk.mltd.data.model.IdolField
import com.mizukikk.mltd.databinding.ItemFilterIdolBinding
import com.mizukikk.mltd.ui.recyclerview.BaseViewHolder

class FilterIdolAdapter(private val filterArray: Array<String>, private val filterType: Int) :
    RecyclerView.Adapter<FilterIdolAdapter.FilterIdolHolder>() {

    companion object {
        const val FILTER_TYPE_IDOL_TYPE = 1111
        const val FILTER_TYPE_CENTER_EFFECT = 1112
        const val FILTER_TYPE_EXTRA_TYPE = 1113
        const val FILTER_TYPE_RARITY = 1114
        const val FILTER_TYPE_SKILL = 1115
    }

    private var filterList = when (filterType) {
        FILTER_TYPE_CENTER_EFFECT ->
            IdolField.CenterEffectAttribute.ARRAY.toMutableList()
        FILTER_TYPE_EXTRA_TYPE ->
            IdolField.ExtraType.ARRAY.toMutableList()
        FILTER_TYPE_IDOL_TYPE ->
            IdolField.CenterEffectAttribute.ARRAY.toMutableList()
        FILTER_TYPE_RARITY ->
            IdolField.Rarity.ARRAY.toMutableList()
        FILTER_TYPE_SKILL ->
            IdolField.Skill.ARRAY.toMutableList()
        else -> mutableListOf()
    }
    private var tempFilterList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterIdolHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FilterIdolHolder(ItemFilterIdolBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = filterArray.size

    override fun onBindViewHolder(holder: FilterIdolHolder, position: Int) {
        val text = filterArray[position]
        holder.bindData(text)
    }

    fun checkFilterBtStatus() {
        val newList = mutableListOf<Int>()
        newList.addAll(filterList)
        tempFilterList = newList
        notifyDataSetChanged()
    }

    fun getFilterList() {
        val newList = mutableListOf<Int>()
        newList.addAll(tempFilterList)
        filterList = newList
        notifyDataSetChanged()
    }

    inner class FilterIdolHolder(binding: ViewDataBinding) :
        BaseViewHolder<ItemFilterIdolBinding>(binding) {
        fun bindData(text: String) {
            binding.tvFilter.text = text
            val filterData = getFilterData()
            binding.filterSelect = filterList.contains(filterData)
            binding.tvFilter.setOnClickListener {
                when (binding.tvFilter.currentTextColor) {
                    getColor(R.color.white) -> {
                        binding.filterSelect = false
                        if (tempFilterList.contains(filterData))
                            tempFilterList.remove(filterData)
                    }
                    getColor(R.color.gray) -> {
                        binding.filterSelect = true
                        if (tempFilterList.contains(filterData).not())
                            tempFilterList.add(filterData)

                    }
                }
            }
        }

        private fun getFilterData(): Int {
            return when (filterType) {
                FILTER_TYPE_CENTER_EFFECT ->
                    IdolField.CenterEffectAttribute.ARRAY[adapterPosition]
                FILTER_TYPE_EXTRA_TYPE ->
                    IdolField.ExtraType.ARRAY[adapterPosition]
                FILTER_TYPE_IDOL_TYPE ->
                    IdolField.CenterEffectAttribute.ARRAY[adapterPosition]
                FILTER_TYPE_RARITY ->
                    IdolField.Rarity.ARRAY[adapterPosition]
                FILTER_TYPE_SKILL ->
                    IdolField.Skill.ARRAY[adapterPosition]
                else -> -1
            }
        }

        fun getColor(@ColorRes colorRes: Int) =
            ContextCompat.getColor(binding.root.context, colorRes)
    }
}