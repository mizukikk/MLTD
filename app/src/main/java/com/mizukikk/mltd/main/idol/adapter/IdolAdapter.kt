package com.mizukikk.mltd.main.idol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mizukikk.mltd.databinding.ItemIdolBinding
import com.mizukikk.mltd.ui.recyclerview.BaseViewHolder

class IdolAdapter : RecyclerView.Adapter<IdolAdapter.IdolHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdolHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IdolHolder(ItemIdolBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = 10

    override fun onBindViewHolder(holder: IdolHolder, position: Int) {
        holder.bindData()
    }

    inner class IdolHolder(binding: ItemIdolBinding) :
        BaseViewHolder<ItemIdolBinding>(binding) {
        fun bindData() {

        }
    }
}