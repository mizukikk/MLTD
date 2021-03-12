package com.mizukikk.mltd.api.obj

import android.content.res.Configuration
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.R
import com.mizukikk.mltd.extension.date2Millis
import com.mizukikk.mltd.extension.millis2Date
import kotlinx.android.parcel.Parcelize
import java.util.*
import java.util.concurrent.TimeUnit


@Parcelize
data class Schedule(
        @Expose
        @SerializedName("beginDate")
        var beginDate: String,
        @Expose
        @SerializedName("boostBeginDate")
        var boostBeginDate: String,
        @Expose
        @SerializedName("boostEndDate")
        var boostEndDate: String,
        @Expose
        @SerializedName("endDate")
        var endDate: String,
        @Expose
        @SerializedName("pageBeginDate")
        var pageBeginDate: String,
        @Expose
        @SerializedName("pageEndDate")
        var pageEndDate: String
) : Parcelable {

    private val beginDateMillis by lazy { beginDate.date2Millis() }
    private val boostDateMillis by lazy { boostBeginDate.date2Millis() }

    val maximumDrawXAxisCount by lazy {
        val start = beginDate.date2Millis()
        val end = endDate.date2Millis()
        val current = System.currentTimeMillis()
        val count = if (current > end) {
            (end - start) / TimeUnit.MINUTES.toMillis(30)
        } else {
            (current - start) / TimeUnit.MINUTES.toMillis(30)
        }
        count.toFloat()
    }
    val chartDateCount by lazy {
        val start = beginDate.date2Millis()
        val end = endDate.date2Millis()
        val count = (end - start) / TimeUnit.MINUTES.toMillis(30)
        count.toFloat()
    }
    val chartLabelCount
        get() :Int {
            val totalCount = chartDateCount.toInt()
            return if (totalCount % 48 == 0)
                totalCount / 48
            else
                totalCount / 48 + 1
        }

    private var boostCount = 0.0f
    val chartBoostCount
        get() =
            if (boostCount != 0.0f) {
                boostCount
            } else {
                calculateBoostCount()
            }

    private fun calculateBoostCount(): Float {
        for (i in 0..chartDateCount.toInt()) {
            val eventCountMillis = beginDateMillis + i * TimeUnit.MINUTES.toMillis(30)
            if (eventCountMillis == boostDateMillis) {
                boostCount = i.toFloat()
                return boostCount
            }
        }
        return 0f
    }

    fun getChartXAxisDate(pos: Float, showTime: Boolean = false): String {
        val eventCountMillis = (pos * TimeUnit.MINUTES.toMillis(30)).toLong()
        val xAxisMillis = beginDateMillis + eventCountMillis
        return if (
                MLTDApplication.appContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                || showTime
        )
            xAxisMillis.millis2Date("MM/dd HH:mm", TimeZone.getDefault().id)
        else
            xAxisMillis.millis2Date("MM/dd", TimeZone.getDefault().id)
    }

    val eventDate
        get() :String {
            val start = beginDate.date2Millis().millis2Date("yyyy/MM/dd HH:mm", TimeZone.getDefault().id)
            val end = endDate.date2Millis().millis2Date("yyyy/MM/dd HH:mm", TimeZone.getDefault().id)
            return MLTDApplication.appContext.getString(R.string.fragment_event_detail_event_date).format("$start - $end")
        }
    val boostDate
        get() :String? {
            return try {
                val boost = boostBeginDate.date2Millis().millis2Date("yyyy/MM/dd HH:mm", TimeZone.getDefault().id)
                MLTDApplication.appContext.getString(R.string.fragment_event_detail_event_boost_date).format(boost)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    val inProgress get() = System.currentTimeMillis() in beginDate.date2Millis()..endDate.date2Millis()
}

