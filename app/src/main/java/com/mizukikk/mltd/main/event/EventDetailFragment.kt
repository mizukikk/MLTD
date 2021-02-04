package com.mizukikk.mltd.main.event

import android.os.Bundle
import android.view.View
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentEventDetailBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.event.model.EventDetailData
import com.mizukikk.mltd.main.event.viewmodel.EventDetailViewModel

class EventDetailFragment :
    BaseMainFragment<EventDetailViewModel, FragmentEventDetailBinding>(R.layout.fragment_event_detail) {

    companion object {
        private const val EVENT_DETAIL_DATA = "EventDetailData"
        fun newInstance(data: EventDetailData) = EventDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EVENT_DETAIL_DATA, data)
            }
        }
    }

    override fun viewModelClass() = EventDetailViewModel::class.java

    override fun initBinding(view: View) = FragmentEventDetailBinding.bind(view)

    override fun init() {

    }
}