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
import com.mizukikk.mltd.main.idol.model.*
import com.mizukikk.mltd.ui.recyclerview.BaseViewHolder

class FilterIdolAdapter(private val filterArray: List<Any>, private val filterType: Int) :
    RecyclerView.Adapter<FilterIdolAdapter.FilterIdolHolder>() {

    companion object {
        const val FILTER_TYPE_IDOL_TYPE = 1111
        const val FILTER_TYPE_CENTER_EFFECT = 1112
        const val FILTER_TYPE_EXTRA_TYPE = 1113
        const val FILTER_TYPE_RARITY = 1114
        const val FILTER_TYPE_SKILL = 1115
        const val FILTER_TYPE_SKILL_DURATION = 1116
    }

    private var filterList = mutableListOf<Int>()
    private var tempFilterList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterIdolHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FilterIdolHolder(ItemFilterIdolBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = filterArray.size

    override fun onBindViewHolder(holder: FilterIdolHolder, position: Int) {
        val data = filterArray[position]
        holder.bindData(data)
    }

    fun checkFilterBtStatus() {
        val newList = mutableListOf<Int>()
        newList.addAll(filterList)
        tempFilterList = newList
        notifyDataSetChanged()
    }

    fun getFilterList(): List<Int> {
        val newList = mutableListOf<Int>()
        newList.addAll(tempFilterList)
        filterList = newList
        notifyDataSetChanged()
        return filterList
    }

    fun clearFilterList() {
        filterList = mutableListOf()
        tempFilterList = mutableListOf()
        notifyDataSetChanged()
    }

    inner class FilterIdolHolder(binding: ViewDataBinding) :
        BaseViewHolder<ItemFilterIdolBinding>(binding) {
        fun bindData(data: Any) {
            binding.tvFilter.text = when (data) {
                is CenterEffect -> data.centerEffectValue
                is ExtraType -> data.extraTypeValue
                is IdolType -> data.type
                is Skill -> data.skillValue
                is Rarity -> data.rarity
                is Int -> data.toString()
                else -> null
            }
            val filterDataList = when (data) {
                is CenterEffect -> data.value
                is ExtraType -> data.value
                is IdolType -> data.value
                is Skill -> data.value
                is Rarity -> data.value
                is Int -> mutableListOf(data)
                else -> mutableListOf()
            }
            binding.filterSelect = filterList.containsAll(filterDataList)
            binding.tvFilter.setOnClickListener {
                when (binding.tvFilter.currentTextColor) {
                    getColor(R.color.white) -> {
                        binding.filterSelect = false
                        if (tempFilterList.containsAll(filterDataList))
                            tempFilterList.removeAll(filterDataList)
                    }
                    getColor(R.color.gray) -> {
                        binding.filterSelect = true
                        if (tempFilterList.containsAll(filterDataList).not())
                            tempFilterList.addAll(filterDataList)

                    }
                }
            }
        }


        fun getColor(@ColorRes colorRes: Int) =
            ContextCompat.getColor(binding.root.context, colorRes)
    }
}