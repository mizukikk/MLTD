package com.mizukikk.mltd.chart.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.R
import com.mizukikk.mltd.api.obj.EventPoint
import com.mizukikk.mltd.api.obj.Schedule

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
        val CHART_COLOR_ARRAY by lazy {
            MLTDApplication.appContext.resources.getIntArray(R.array.chartColorArray)
        }
        private const val EVENT_ONE_DAY_COUNT = 48f
        private const val DASH_LINE = 10f
        private const val DASH_SPACE = 10f
        private const val DASH_PHASE = 0f
        private const val CHART_ANIM_DURATION = 300
    }

    private var eventSchedule: Schedule? = null
    private var eventBorderLogList: List<EventPoint>? = null
    private var filterRankMap: HashMap<Int, Boolean>? = null

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
        legend.isEnabled = false
        //透明背景
        setBackgroundColor(Color.TRANSPARENT)
    }

    private fun setXAxisSetting() {
        xAxis.apply {
            enableGridDashedLine(DASH_LINE, DASH_SPACE, DASH_PHASE)
            position = XAxis.XAxisPosition.BOTTOM
            axisLineWidth = 1f
            gridLineWidth = 1f
            offsetLeftAndRight(100)
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return eventSchedule?.getChartXAxisDate(value) ?: ""
                }
            }
        }
    }

    private fun setYAxisSetting() {
        axisRight.isEnabled = false
        axisLeft.apply {
            enableGridDashedLine(DASH_LINE, DASH_SPACE, DASH_PHASE)
            axisMinimum = 0.0f
            axisLineWidth = 1f
            gridLineWidth = 1f
        }
    }

    fun setBorderLog(borderLogList: List<EventPoint>, schedule: Schedule) {
        eventSchedule = schedule
        eventBorderLogList = borderLogList
        setXAxisInterval()
        setBoostLimitLine()
        setEventData()
        drawChart()
    }

    fun filterBorderLog(filterRankMap: HashMap<Int, Boolean>) {
        this.filterRankMap = filterRankMap
        setEventData()
        drawChart()
    }

    private fun drawChart() {
        animateX(CHART_ANIM_DURATION)
        invalidate()
    }

    private fun setEventData() {
        if (eventBorderLogList == null)
            return
        val lineData = LineData()
        eventBorderLogList!!
                .forEachIndexed { index, eventPoint ->
                    if (filterRankMap?.containsKey(eventPoint.rank) != true) {
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
                            setDrawHorizontalHighlightIndicator(false)
                            color = CHART_COLOR_ARRAY[index]
                            highLightColor = Color.BLACK
                        }
                        lineData.addDataSet(lineDataSet)
                    }
                }
        data = lineData
    }

    private fun setBoostLimitLine() {
        if (eventSchedule == null)
            return
        eventSchedule!!.chartBoostCount.let { boostCount ->
            if (boostCount > 0)
                xAxis.addLimitLine(LimitLine(boostCount, "boost").apply {
                    textColor = Color.RED
                    lineColor = Color.RED
                    enableDashedLine(DASH_LINE, DASH_SPACE, DASH_PHASE)
                    lineWidth = 1f
                })
        }
    }

    private fun setXAxisInterval() {
        if (eventSchedule == null)
            return
        xAxis.apply {
            axisMaximum = eventSchedule!!.chartDateCount
            labelCount = eventSchedule!!.chartLabelCount
            granularity = EVENT_ONE_DAY_COUNT
        }
    }
}