package com.mizukikk.mltd.extension

import android.content.res.ColorStateList
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.core.widget.CompoundButtonCompat
import com.mizukikk.mltd.R

fun CheckBox.setCheckColor(
    checkColor: Int,
    unCheckColor: Int = ContextCompat.getColor(this.context, R.color.gray)
) {
    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        ), intArrayOf(checkColor, unCheckColor)
    )
    CompoundButtonCompat.setButtonTintList(this, colorStateList)
}