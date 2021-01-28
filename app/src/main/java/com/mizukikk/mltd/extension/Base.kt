package com.mizukikk.mltd.extension

import com.mizukikk.mltd.MLTDApplication
import java.text.SimpleDateFormat
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


fun String.date2Millis(pattern: String = "yyyy-MM-dd'T'HH:mm:ssZ", zone: String? = null): Long {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    zone?.let {
        format.timeZone = TimeZone.getTimeZone(zone)
    }
    return format.parse(this)?.time ?: 0L
}

fun Long.millis2Date(pattern: String = "yyyy-MM-dd'T'HH:mm:ssZ", zone: String? = null): String {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    zone?.let {
        format.timeZone = TimeZone.getTimeZone(zone)
    }
    return format.format(this)
}