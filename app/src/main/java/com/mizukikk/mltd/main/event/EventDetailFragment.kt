package com.mizukikk.mltd.main.event

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentEventDetailBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.event.adapter.EventBorderAdapter
import com.mizukikk.mltd.main.event.dialog.AnivIdolListDialog
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
    private var eventBorderAdapter: EventBorderAdapter? = null

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
        setListener()
        viewModel.getEventBorderData(data.eventData)
    }

    private fun setListener() {
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = true
            viewModel.getEventBorderData(data.eventData)
        }
    }

    private fun initViewModel() {
        viewModel.eventBorderLiveData.observe(this, Observer { eventBorderList ->
            if (binding.refresh.isRefreshing)
                binding.refresh.isRefreshing = false
            if (eventBorderList != null) {
                if (eventBorderAdapter == null) {
                    eventBorderAdapter = EventBorderAdapter(
                        eventBorderList,
                        data.eventData.schedule.inProgress
                    )
                    binding.rvEventBorder.adapter = eventBorderAdapter
                    eventBorderAdapter!!.setListener(object :
                        EventBorderAdapter.EventBorderListener {
                        override fun selectAnivIdol() {
                            viewModel.getAnivIdolList()
                        }
                    })
                } else {
                    eventBorderAdapter?.swapData(eventBorderList)
                }
            }
        })
        viewModel.anivIdolListLiveData.observe(this, Observer {
            AnivIdolListDialog.newInstance(it)
                .setAnivIdolListDialogListener { idolId ->
                    binding.refresh.isRefreshing = true
                    viewModel.changeAnivIDolBorder(data.eventData, idolId)
                }
                .show(requireFragmentManager())
        })
    }

    private fun initView() {
        binding.refresh.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorPrimary
            )
        )
        binding.refresh.isRefreshing = true
        binding.eventData = data.eventData
        binding.rvEventBorder.layoutManager = LinearLayoutManager(requireContext())
    }
}