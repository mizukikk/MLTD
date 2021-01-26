package com.mizukikk.mltd.main.idol.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.mizukikk.mltd.databinding.ItemIdolBinding
import com.mizukikk.mltd.main.idol.model.FilterIdolData
import com.mizukikk.mltd.room.query.IdolItem
import com.mizukikk.mltd.ui.recyclerview.BaseViewHolder

class IdolAdapter : RecyclerView.Adapter<IdolAdapter.IdolHolder>() {

    private var listener: ((View, IdolItem) -> Unit?)? = null
    private var idolList: List<IdolItem> = mutableListOf()
    private var filterList: List<IdolItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdolHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IdolHolder(ItemIdolBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = filterList.size

    override fun onBindViewHolder(holder: IdolHolder, position: Int) {
        val data = filterList[position]
        holder.bindData(data)
    }

    fun swapData(newIdolList: List<IdolItem>) {
        this.idolList = newIdolList
        filterList = newIdolList
        notifyDataSetChanged()
    }

    fun setIdolListListener(listener: ((View, IdolItem) -> Unit)) {
        this.listener = listener
    }

    fun search(searchText: String, filterData: FilterIdolData?) {
        synchronized(this) {
            filterList = idolList.filter {
                if (searchText.isEmpty())
                    true
                else
                    it.idol.name.toLowerCase().contains(searchText.toLowerCase())
            }
            filterData?.let {
                if (filterData.idolTypeFilterList.isNotEmpty())
                    filterList = filterList.filter {
                        filterData.idolTypeFilterList.contains(it.idol.idolType)
                    }
                if (filterData.centerEffectFilterList.isNotEmpty())
                    filterList = filterList.filter {
                        filterData.centerEffectFilterList.contains(it.centerEffectEntity?.attribute)
                    }
                if (filterData.extraTypeFilterList.isNotEmpty())
                    filterList = filterList.filter {
                        filterData.extraTypeFilterList.contains(it.idol.extraType)
                    }
                if (filterData.rarityFilterList.isNotEmpty())
                    filterList = filterList.filter {
                        filterData.rarityFilterList.contains(it.idol.rarity)
                    }
                if (filterData.skillFilterList.isNotEmpty())
                    filterList = filterList.filter {
                        filterData.skillFilterList.contains(it.skill?.effectId)
                    }
                if (filterData.skillDurationFilterList.isNotEmpty())
                    filterList = filterList.filter {
                        filterData.skillDurationFilterList.contains(it.skill?.interval)
                    }
            }
            notifyDataSetChanged()
        }
    }

    fun filter() {
        synchronized(this) {
            filterList = filterList.filter { true }
            notifyDataSetChanged()
        }
    }

    fun clearSearch() {
        filterList = idolList
        notifyDataSetChanged()
    }

    inner class IdolHolder(binding: ItemIdolBinding) : BaseViewHolder<ItemIdolBinding>(binding) {
        fun bindData(data: IdolItem) {
            binding.data = data
            binding.executePendingBindings()
            ViewCompat.setTransitionName(binding.root, data.idol.resourceId)
            binding.root.setOnClickListener {
                listener?.invoke(it, data)
            }
        }
    }
}