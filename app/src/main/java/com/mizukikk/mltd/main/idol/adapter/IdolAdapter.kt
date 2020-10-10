package com.mizukikk.mltd.main.idol.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.mizukikk.mltd.databinding.ItemIdolBinding
import com.mizukikk.mltd.room.query.IdolItem
import com.mizukikk.mltd.ui.recyclerview.BaseViewHolder

class IdolAdapter : RecyclerView.Adapter<IdolAdapter.IdolHolder>() {

    private var listener: ((View, IdolItem) -> Unit?)? = null
    private var idolList: List<IdolItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdolHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IdolHolder(ItemIdolBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = idolList.size

    override fun onBindViewHolder(holder: IdolHolder, position: Int) {
        val data = idolList[position]
        holder.bindData(data)
    }

    fun swapData(newIdolList: List<IdolItem>) {
        this.idolList = newIdolList
        notifyDataSetChanged()
    }

    fun setIdolListListener(listener: ((View, IdolItem) -> Unit)) {
        this.listener = listener
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