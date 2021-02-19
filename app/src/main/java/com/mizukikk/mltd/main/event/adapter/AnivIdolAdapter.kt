package com.mizukikk.mltd.main.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mizukikk.mltd.databinding.ItemIdolIconBinding
import com.mizukikk.mltd.extension.convertDp2Px
import com.mizukikk.mltd.room.query.IdolItem
import com.mizukikk.mltd.ui.recyclerview.BaseViewHolder

class AnivIdolAdapter : RecyclerView.Adapter<AnivIdolAdapter.AnivIdolHolder>() {

    private var listener: ((Int) -> Unit)? = null
    private var itemWH = 0
    private var anivIdolList: List<IdolItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnivIdolHolder {
        if (itemWH == 0)
            itemWH = (parent.width / 4) - 4f.convertDp2Px().toInt()
        val inflater = LayoutInflater.from(parent.context)
        return AnivIdolHolder(ItemIdolIconBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: AnivIdolHolder, position: Int) {
        val data = anivIdolList[position]
        holder.bindData(data)
    }

    override fun getItemCount() = anivIdolList.size

    fun setListener(listener: (idolId: Int) -> Unit) {
        this.listener = listener
    }

    fun swapData(anivIdolList: List<IdolItem>) {
        this.anivIdolList = anivIdolList
        notifyDataSetChanged()
    }


    inner class AnivIdolHolder(binding: ViewDataBinding) : BaseViewHolder<ItemIdolIconBinding>(binding) {

        fun bindData(data: IdolItem) {
            checkItemSize()
            setData(data)
            itemView.setOnClickListener {
                listener?.invoke(data.idol.idolId)
            }
        }

        private fun setData(data: IdolItem) {
            binding.data = data
            binding.executePendingBindings()
        }

        private fun checkItemSize() {
            if (binding.root.width != itemWH) {
                val lp = binding.root.layoutParams
                lp.width = itemWH
                lp.height = itemWH
                binding.root.layoutParams = lp
            }
        }
    }
}