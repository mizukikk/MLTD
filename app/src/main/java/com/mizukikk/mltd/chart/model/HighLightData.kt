package com.mizukikk.mltd.chart.model

import android.text.SpannableStringBuilder

data class HighLightData(
        var rankSpanBuilder: SpannableStringBuilder? = null,
        var pointSpanBuilder: SpannableStringBuilder? = null,
        private var _xPx: Float = 0.0f,
        var borderDate: String = ""
) {

    val xPx get() = _xPx
    val highlightViewVisible get() = rankSpanBuilder != null && pointSpanBuilder != null

    fun setHighlightXPx(xPx: Float) {
        this._xPx = xPx
    }

    fun clear() {
        rankSpanBuilder = null
        pointSpanBuilder = null
        _xPx = 0.0f
    }

}