package com.mizukikk.mltd.main.event

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentEventDetailBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.event.adapter.EventBorderAdapter
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

    private lateinit var data: EventDetailData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable(EVENT_DETAIL_DATA)!!
        }
    }

    override fun viewModelClass() = EventDetailViewModel::class.java

    override fun initBinding(view: View) = FragmentEventDetailBinding.bind(view)

    override fun init() {
        initView()
        initViewModel()
        viewModel.getEventBorderData(data.eventData)
    }

    private fun initViewModel() {
        viewModel.eventBorderLiveData.observe(this, Observer { eventBorderList ->
            binding.progressBar.visibility = View.GONE
            if (eventBorderList != null) {
                binding.rvEventBorder.adapter =
                    EventBorderAdapter(
                        eventBorderList,
                        data.eventData.schedule.inProgress
                    )
            }
        })
    }

    private fun initView() {
        binding.eventData = data.eventData
        binding.rvEventBorder.layoutManager = LinearLayoutManager(requireContext())
    }
}