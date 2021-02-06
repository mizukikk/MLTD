package com.mizukikk.mltd.main.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mizukikk.mltd.R
import com.mizukikk.mltd.api.obj.LastPointData
import com.mizukikk.mltd.databinding.ItemLastPointBinding
import com.mizukikk.mltd.databinding.ItemPointBinding
import com.mizukikk.mltd.ui.recyclerview.BaseViewHolder
import java.text.NumberFormat

class LastPointAdapter(private val lastPointList: List<LastPointData>) :
    RecyclerView.Adapter<LastPointAdapter.LastPointHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastPointHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LastPointHolder(ItemLastPointBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: LastPointHolder, position: Int) {
        holder.bindData(lastPointList[position])
    }

    override fun getItemCount() = lastPointList.size

    inner class LastPointHolder(binding: ViewDataBinding) :
        BaseViewHolder<ItemLastPointBinding>(binding) {
        fun bindData(data: LastPointData) {
            setTitle(data)
            setBorders(data)
        }

        private fun setBorders(data: LastPointData) {
            data.scores.filter { it.rank <= 50000 }.forEach {
                val pointBinding =
                    ItemPointBinding.inflate(LayoutInflater.from(binding.root.context))
                pointBinding.tvNo.text =
                    getString(R.string.item_last_point_rank).format(it.rank)
                pointBinding.tvScore.text = NumberFormat.getNumberInstance().format(it.score)
                binding.llBorders.addView(pointBinding.root)
            }
        }

        private fun setTitle(data: LastPointData) {
            binding.tvTitle.text = data.title
        }
    }

}