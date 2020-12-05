package com.mizukikk.mltd.utils

import android.app.ActivityManager
import android.content.Context
import androidx.core.content.ContextCompat

object ServiceUtils {
    fun isServiceRunning(context: Context, className: String): Boolean {
        val am = ContextCompat.getSystemService(context, ActivityManager::class.java)
        val runningServiceList = am?.getRunningServices(Int.MAX_VALUE)?.map { it.service.className }
        return runningServiceList?.contains(className) ?: false
    }
}