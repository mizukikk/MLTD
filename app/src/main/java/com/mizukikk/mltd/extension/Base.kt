package com.mizukikk.mltd.extension

import com.mizukikk.mltd.MLTDApplication

fun Float.convertDp2Px(): Float {
    val density = MLTDApplication.applicationContext.resources.displayMetrics.density
    return this * density
}