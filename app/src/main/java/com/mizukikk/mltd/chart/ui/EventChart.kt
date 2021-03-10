package com.mizukikk.mltd.chart.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.mizukikk.mltd.R
import com.mizukikk.mltd.api.obj.EventPoint

class EventChart : LineChart {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        cancelZoom()
        setChartSetting()
        setXAxisSetting()
        setYAxisSetting()
    }

    companion object {
        const val X_AXIS_COUNT = 6
    }

    private fun cancelZoom() {
        //取消連點兩下放大功能
        isDoubleTapToZoomEnabled = false
        //取消兩指手勢縮放功能
        setScaleEnabled(false)
    }

    private fun setChartSetting() {
        //取消右下角描述文字
        description.isEnabled = false
        //左下文字自動換行
        legend.isWordWrapEnabled = true
        //透明背景
        setBackgroundColor(Color.TRANSPARENT)
    }

    private fun setXAxisSetting() {
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            axisLineWidth = 1f
            gridLineWidth = 1f
            offsetLeftAndRight(100)
        }
    }

    private fun setYAxisSetting() {
        axisRight.isEnabled = false
        axisLeft.apply {
            axisMinimum = 0.0f
            axisLineColor = Color.TRANSPARENT
            gridLineWidth = 1f
        }
    }

    fun setBorderLog(borderLogList: List<EventPoint>) {
        val lineData = LineData()
        borderLogList.forEach { eventPoint ->
            val label = context.getString(R.string.event_chart_rank_no).format(eventPoint.rank.toString())
            val entryList = mutableListOf<Entry>()
            eventPoint.data.forEachIndexed { index, pointData ->
                val yValue = pointData.score.toFloat()
                val xValue = index.toFloat()
                entryList.add(Entry(xValue, yValue))
            }
            val lineDataSet = LineDataSet(entryList, label).apply {
                setDrawCircleHole(false)
                setDrawCircles(false)
            }
            lineData.addDataSet(lineDataSet)
        }
        data = lineData
        invalidate()
    }

}