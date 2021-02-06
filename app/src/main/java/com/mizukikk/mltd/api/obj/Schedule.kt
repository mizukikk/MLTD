package com.mizukikk.mltd.api.obj

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mizukikk.mltd.extension.date2Millis
import com.mizukikk.mltd.extension.millis2Date
import kotlinx.android.parcel.Parcelize
import java.util.*


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
    val eventDate
        get() :String {
            val start = beginDate.date2Millis().millis2Date("yyyy/MM/dd HH:mm", TimeZone.getDefault().id)
            val end = endDate.date2Millis().millis2Date("yyyy/MM/dd HH:mm", TimeZone.getDefault().id)
            return "$start - $end"
        }
    val inProgress get() = System.currentTimeMillis() in beginDate.date2Millis()..endDate.date2Millis()
}

