package com.mizukikk.mltd.main.idol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.ItemFilterIdolBinding
import com.mizukikk.mltd.ui.recyclerview.BaseViewHolder

class FilterIdolAdapter(private val filterArray: Array<String>, filterType: Int = 0) :
    RecyclerView.Adapter<FilterIdolAdapter.FilterIdolHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterIdolHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FilterIdolHolder(ItemFilterIdolBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = filterArray.size

    override fun onBindViewHolder(holder: FilterIdolHolder, position: Int) {
        val text = filterArray[position]
        holder.bindData(text)
    }

    inner class FilterIdolHolder(binding: ViewDataBinding) :
        BaseViewHolder<ItemFilterIdolBinding>(binding) {
        fun bindData(text: String) {
            binding.tvFilter.text = text
            binding.tvFilter.setOnClickListener {
                when (binding.tvFilter.currentTextColor) {
                    getColor(R.color.white) -> {
                        binding.filterSelect = false
                    }
                    getColor(R.color.gray) -> {
                        binding.filterSelect = true
                    }
                }
            }
        }

        fun getColor(@ColorRes colorRes: Int) =
            ContextCompat.getColor(binding.root.context, colorRes)
    }
}