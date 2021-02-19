package com.mizukikk.mltd.main.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.ItemEventBorderBinding
import com.mizukikk.mltd.databinding.ItemPointBinding
import com.mizukikk.mltd.main.event.model.EventBorder
import com.mizukikk.mltd.ui.recyclerview.BaseViewHolder

class EventBorderAdapter(
        private var eventBorderList: List<EventBorder>,
        private val inProgress: Boolean
) :
        RecyclerView.Adapter<EventBorderAdapter.EventBorderHolder>() {
    private var listener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventBorderHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventBorderHolder(ItemEventBorderBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: EventBorderHolder, position: Int) {
        holder.bindData(eventBorderList[position])
    }

    override fun getItemCount() = eventBorderList.size

    fun swapData(eventBorderList: List<EventBorder>) {
        this.eventBorderList = eventBorderList
        notifyDataSetChanged()
    }

    fun clear() {
        eventBorderList = mutableListOf()
        notifyDataSetChanged()
    }

    fun setListener(listener: () -> Unit) {
        this.listener = listener
    }

    inner class EventBorderHolder(binding: ViewDataBinding) : BaseViewHolder<ItemEventBorderBinding>(binding) {
        fun bindData(data: EventBorder) {
            setTitle(data)
            setBorders(data)
            setIdolData(data)
            setListener()
        }

        private fun setListener() {
            binding.flIcon.setOnClickListener {
                listener?.invoke()
            }
        }

        private fun setIdolData(data: EventBorder) {
            data.idolData?.let {
                binding.data = it
            }
        }

        private fun setBorders(data: EventBorder) {
            binding.llBorders.removeAllViews()
            data.borderList.filter { it.rank <= 50000 }.forEachIndexed { index, border ->
                val pointBinding = ItemPointBinding.inflate(LayoutInflater.from(binding.root.context))
                pointBinding.tvNo.text =
                        getString(R.string.item_last_point_rank).format(border.rank)
                pointBinding.tvScore.text = border.currentScore
                pointBinding.tvHalfHourAdd.text = border.halfHour
                pointBinding.tvOneHourAdd.text = border.oneHour
                pointBinding.tvOneDayAdd.text = border.oneDay
                binding.llBorders.addView(pointBinding.root)
            }
        }

        private fun setTitle(data: EventBorder) {
            binding.tvTitle.text = data.title
            binding.tvUpdate.text = getString(R.string.item_last_point_update).format(data.updateDate)
        }
    }

}