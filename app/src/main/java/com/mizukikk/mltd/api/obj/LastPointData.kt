package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mizukikk.mltd.extension.date2Millis
import com.mizukikk.mltd.extension.millis2Date
import com.mizukikk.mltd.main.event.model.Border
import java.util.*

data class LastPointData(
    @Expose
    @SerializedName("count")
    val count: Int,
    @Expose
    @SerializedName("scores")
    val scores: List<Score>,
    @Expose
    @SerializedName("summaryTime")
    val summaryTime: String,
    var title: String?
) {
    val updateDate
        get() = summaryTime
            .date2Millis()
            .millis2Date("yyyy/MM/dd HH:mm", TimeZone.getDefault().id)
    val borderList get() = scores.map { Border(it.rank, it.score) }
}