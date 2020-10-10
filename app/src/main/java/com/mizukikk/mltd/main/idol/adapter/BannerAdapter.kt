package com.mizukikk.mltd.main.idol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mizukikk.mltd.databinding.ItemBannerBinding
import com.mizukikk.mltd.room.entity.IdolEntity
import com.mizukikk.mltd.ui.recyclerview.BaseViewHolder

class BannerAdapter(private val data: IdolEntity) :
    RecyclerView.Adapter<BannerAdapter.BannerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BannerHolder(ItemBannerBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = 2

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        holder.loadImg()
    }

    inner class BannerHolder(binding: ViewDataBinding) :
        BaseViewHolder<ItemBannerBinding>(binding) {
        fun loadImg() {
            binding.cardBGUrl = if (adapterPosition == 0)
                data.cardWithSignedUrl
            else
                data.cardWithSignedWakenedUrl
        }
    }
}