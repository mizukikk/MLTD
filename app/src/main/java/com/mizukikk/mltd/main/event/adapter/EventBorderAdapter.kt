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
) : RecyclerView.Adapter<EventBorderAdapter.EventBorderHolder>() {

    private var listener: EventBorderListener? = null

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

    fun setListener(listener: EventBorderListener) {
        this.listener = listener
    }

    interface EventBorderListener {
        fun selectAnivIdol()
        fun showEventChart(eventChartType: String, eventChartRanks: String)
    }

    inner class EventBorderHolder(binding: ViewDataBinding) : BaseViewHolder<ItemEventBorderBinding>(binding) {
        fun bindData(data: EventBorder) {
            setTitle(data)
            setBorders(data)
            setIdolData(data)
            setListener(data)
        }

        private fun setListener(data: EventBorder) {
            binding.flIcon.setOnClickListener {
                listener?.selectAnivIdol()
            }
            binding.root.setOnClickListener {
                listener?.showEventChart(data.eventChartType, data.eventChartRanks)
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
                val pointBinding =
                        ItemPointBinding.inflate(LayoutInflater.from(binding.root.context))
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
            binding.tvUpdate.text =
                    getString(R.string.item_last_point_update).format(data.updateDate)
        }
    }

}