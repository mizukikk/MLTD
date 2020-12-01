package com.mizukikk.mltd.extension

import com.mizukikk.mltd.MLTDApplication
import java.util.*

fun Float.convertDp2Px(): Float {
    val density = MLTDApplication.appContext.resources.displayMetrics.density
    return this * density
}

fun Long.nextUpdateTimeMillis(): Long {
    return Calendar.getInstance().apply {
        timeInMillis = this@nextUpdateTimeMillis
        set(Calendar.HOUR_OF_DAY, 15)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        add(Calendar.DAY_OF_MONTH, 1)
    }.timeInMillis
}