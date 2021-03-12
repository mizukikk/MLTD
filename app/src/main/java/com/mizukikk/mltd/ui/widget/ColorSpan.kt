package com.mizukikk.mltd.ui.widget

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class ColorSpan(private val textColor: Int) : ClickableSpan() {
    override fun onClick(widget: View) {}
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
        ds.color = textColor
    }
}