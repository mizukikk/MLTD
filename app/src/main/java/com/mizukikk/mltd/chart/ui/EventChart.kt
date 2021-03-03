package com.mizukikk.mltd.chart.ui

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.LineChart

class EventChart : LineChart {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {

    }

}