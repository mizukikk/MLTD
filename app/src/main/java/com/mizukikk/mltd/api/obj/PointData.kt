package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mizukikk.mltd.extension.date2Millis
import com.mizukikk.mltd.extension.millis2Date
import java.util.*

data class PointData(
    @Expose
    @SerializedName("count")
    val count: Int?,
    @Expose
    @SerializedName("score")
    val score: Double,
    @Expose
    @SerializedName("summaryTime")
    val summaryTime: String
) {
    val updateDate
        get() = summaryTime
            .date2Millis()
            .millis2Date("yyyy/MM/dd HH:mm", TimeZone.getDefault().id)
}
