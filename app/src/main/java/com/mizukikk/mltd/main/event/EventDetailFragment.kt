package com.mizukikk.mltd.main.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentEventDetailBinding
import com.mizukikk.mltd.databinding.ItemPointBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.event.adapter.LastPointAdapter
import com.mizukikk.mltd.main.event.model.EventDetailData
import com.mizukikk.mltd.main.event.model.LastPoint
import com.mizukikk.mltd.main.event.viewmodel.EventDetailViewModel
import java.text.NumberFormat

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
        viewModel.getLastEventData(data.eventData)
    }

    private fun initViewModel() {
        viewModel.lastPointLiveData.observe(this, Observer { lastPointData ->
            binding.progressBar.visibility = View.GONE
            if (lastPointData != null) {
                setEventBorders(lastPointData)
                if (lastPointData.hasAnivData) {
                    binding.anivEventBorder.root.visibility = View.VISIBLE
                    binding.anivEventBorder.data = lastPointData.anivIdolData
                    setBorders(lastPointData)
                    setTitle(lastPointData)
                }
            }
        })
    }

    private fun setBorders(lastPointData: LastPoint) {
        lastPointData.anivIdolPointList!!.forEach {
            val pointBinding =
                ItemPointBinding.inflate(LayoutInflater.from(binding.root.context))
            pointBinding.tvNo.text =
                getString(R.string.item_last_point_rank).format(it.rank)
            pointBinding.tvScore.text =
                NumberFormat.getNumberInstance().format(it.data.maxBy { it.score }!!.score.toInt())
            binding.anivEventBorder.llBorders.addView(pointBinding.root)
        }
    }

    private fun setTitle(lastPointData: LastPoint) {
        binding.anivEventBorder.tvTitle.text = lastPointData.anivIdolData!!.idolName
        if (data.eventData.schedule.inProgress) {
            binding.anivEventBorder.tvUpdate.text =
                getString(R.string.item_last_point_update).format(lastPointData.updateDate)
        }
    }

    private fun setEventBorders(lastPointData: LastPoint) {
        binding.rvEventBorder.adapter =
            LastPointAdapter(
                lastPointData.lastPointList,
                data.eventData.schedule.inProgress
            )
    }

    private fun initView() {
        binding.eventData = data.eventData
        binding.rvEventBorder.layoutManager = LinearLayoutManager(requireContext())
    }
}