package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mizukikk.mltd.extension.date2Millis
import com.mizukikk.mltd.extension.millis2Date


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
) {
    val eventDate
        get() :String {
            val start = beginDate.date2Millis().millis2Date("yyyy/MM/dd HH:mm", "GMT+9")
            val end = endDate.date2Millis().millis2Date("yyyy/MM/dd HH:mm", "GMT+9")
            return "$start - $end"
        }
}

