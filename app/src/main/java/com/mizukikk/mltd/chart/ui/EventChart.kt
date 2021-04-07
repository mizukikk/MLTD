package com.mizukikk.mltd.chart.ui

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.R
import com.mizukikk.mltd.api.obj.EventPoint
import com.mizukikk.mltd.api.obj.Schedule
import com.mizukikk.mltd.chart.model.HighLightData
import com.mizukikk.mltd.ui.widget.ColorSpan
import java.text.NumberFormat

class EventChart : LineChart, OnChartValueSelectedListener {

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

    private val TAG = EventChart::class.java.simpleName
    private var eventSchedule: Schedule? = null
    private var eventBorderLogList: List<EventPoint>? = null
    private var filterRankMap: HashMap<Int, Boolean>? = null
    private var highlightListener: ((highLightData: HighLightData?) -> Unit)? = null
    private val highLightData by lazy { HighLightData() }

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
        setOnChartValueSelectedListener(this)
        setNoDataText(context.getString(R.string.event_chart_data_empty))
        setNoDataTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        setHighlightSpan(e)
        setHighlightXPx(h)
        setBorderDate(e)
        highlightListener?.invoke(highLightData)
    }

    private fun setHighlightXPx(h: Highlight?) {
        highLightData.setHighlightXPx(h?.xPx ?: 0.0f)
    }

    private fun setBorderDate(e: Entry?) {
        highLightData.borderDate = try {
            eventSchedule!!.getChartXAxisDate(e!!.x, true)
        } catch (e: NullPointerException) {
            ""
        }
    }

    private fun setHighlightSpan(e: Entry?) {
        val xValue = e?.x ?: 0.0f
        val rankSpanBuilder = SpannableStringBuilder()
        val pointSpanBuilder = SpannableStringBuilder()
        for (i in 0 until data.dataSetCount) {
            val lineDataSet = data.getDataSetByIndex(i)
            val entryList = lineDataSet.getEntriesForXValue(xValue)
            if (entryList.isNotEmpty()) {
                val entry = entryList[0]
                val rank = context.getString(R.string.event_chart_rank_no).format(entry.data.toString())
                val point = NumberFormat.getInstance().format(entry.y.toInt())
                appendSpanBuilder(rankSpanBuilder, rank, lineDataSet)
                appendSpanBuilder(pointSpanBuilder, "　$point", lineDataSet)
            }
        }
        highLightData.rankSpanBuilder = rankSpanBuilder
        highLightData.pointSpanBuilder = pointSpanBuilder
    }

    private fun appendSpanBuilder(spanBuilder: SpannableStringBuilder, text: String, lineDataSet: ILineDataSet) {
        val startPos = spanBuilder.length
        if (spanBuilder.isNotEmpty())
            spanBuilder.append("\n")
        spanBuilder.append(text)
        val endPos = spanBuilder.length
        spanBuilder.setSpan(ColorSpan(lineDataSet.color), startPos, endPos, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    override fun onNothingSelected() {
        highLightData.clear()
        highlightListener?.invoke(highLightData)
    }

    fun setHighListener(highlightListener: ((HighLightData?) -> Unit)) {
        this.highlightListener = highlightListener
    }

    private fun setXAxisSetting() {
        xAxis.apply {
            enableGridDashedLine(DASH_LINE, DASH_SPACE, DASH_PHASE)
            position = XAxis.XAxisPosition.BOTTOM
            axisLineWidth = 1f
            gridLineWidth = 1f
            labelRotationAngle = 45f
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
        if (eventBorderLogList == null || eventSchedule == null)
            return

        val lineData = LineData()

        eventBorderLogList!!
                .forEachIndexed { index, eventPoint ->
                    if (filterRankMap?.containsKey(eventPoint.rank) != true) {
                        val label = context.getString(R.string.activity_event_chart_rank_no).format(eventPoint.rank.toString())
                        val entryList = mutableListOf<Entry>()
                        for (i in 0..eventSchedule!!.chartDateCount.toInt()) {
                            val pointData = try {
                                eventPoint.data[i]
                            } catch (e: IndexOutOfBoundsException) {
                                if (i <= eventSchedule!!.maximumDrawXAxisCount)
                                    eventPoint.data.last()
                                else
                                    null
                            }
                            if (pointData != null) {
                                val yValue = pointData.score.toFloat()
                                val entry = Entry(i.toFloat(), yValue).apply {
                                    this.data = eventPoint.rank
                                }
                                entryList.add(entry)
                            }
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