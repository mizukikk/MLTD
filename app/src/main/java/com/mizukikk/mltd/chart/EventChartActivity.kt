package com.mizukikk.mltd.chart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mizukikk.mltd.R
import com.mizukikk.mltd.api.obj.EventPoint
import com.mizukikk.mltd.chart.model.EventChartData
import com.mizukikk.mltd.chart.viewmodel.EventChartViewModel
import com.mizukikk.mltd.databinding.ActivityEventChartBinding
import com.mizukikk.mltd.extension.setCheckColor

class EventChartActivity : AppCompatActivity() {

    companion object {
        private const val EVENT_CHART_DATA = "eventChardData"
        fun newIntent(activity: Activity, data: EventChartData) = Intent(activity, EventChartActivity::class.java).apply {
            putExtra(EVENT_CHART_DATA, data)
        }

    }

    private lateinit var binding: ActivityEventChartBinding
    private lateinit var viewModel: EventChartViewModel
    private var data: EventChartData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        initView()
        initViewModel()
        checkIntentExtra()
    }

    private fun setFullScreen() {
        window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun checkIntentExtra() {
        data = intent?.getParcelableExtra(EVENT_CHART_DATA)
        if (data != null) {
            viewModel.getEventBorders(data!!)
        } else {
            finish()
        }
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_chart)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this)
                .get(EventChartViewModel::class.java)
        viewModel.eventBordersLiveData.observe(this, Observer {
            binding.eventChart.setBorderLog(it, data!!.schedule)
            restoreEventChartRankState()
            if (binding.flRanks.childCount == 0) {
                addRankCheckBox(it)
            }
        })
    }

    private fun restoreEventChartRankState() {
        if (viewModel.filterRankMap.isNotEmpty()) {
            binding.eventChart.filterBorderLog(viewModel.filterRankMap)
        }
    }

    private fun addRankCheckBox(it: List<EventPoint>) {
        val colorArray = resources.getIntArray(R.array.chartColorArray)
        it.forEachIndexed { index, eventPoint ->
            CheckBox(this).apply {
                setCheckColor(colorArray[index])
                text = getString(R.string.event_chart_rank_no).format(eventPoint.rank.toString())
                isChecked = viewModel.filterRankMap.containsKey(eventPoint.rank).not()
                setOnClickListener {
                    if (isChecked) {
                        viewModel.filterRankMap.remove(eventPoint.rank)
                    } else {
                        viewModel.filterRankMap.put(eventPoint.rank, true)
                    }
                    binding.eventChart.filterBorderLog(viewModel.filterRankMap)
                }
                binding.flRanks.addView(this)
            }
        }
    }
}