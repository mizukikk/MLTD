package com.mizukikk.mltd.main.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mizukikk.mltd.api.response.EventResponse
import com.mizukikk.mltd.databinding.ItemEventBinding
import com.mizukikk.mltd.ui.recyclerview.BaseViewHolder

class EventAdapter(private val eventList: List<EventResponse>) :
    RecyclerView.Adapter<EventAdapter.EventHolder>() {

    private var listener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventHolder(ItemEventBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = eventList.size

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bindData(eventList[position])
    }

    fun setListener(listener: (id: Int) -> Unit) {
        this.listener = listener
    }

    inner class EventHolder(binding: ViewDataBinding) : BaseViewHolder<ItemEventBinding>(binding) {
        fun bindData(data: EventResponse) {
            itemView.setOnClickListener {
                listener?.invoke(data.id)
            }
            setData(data)
        }

        private fun setData(data: EventResponse) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

}