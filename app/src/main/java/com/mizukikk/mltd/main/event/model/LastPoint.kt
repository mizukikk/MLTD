package com.mizukikk.mltd.main.event.model

import com.mizukikk.mltd.api.obj.EventPoint
import com.mizukikk.mltd.api.obj.LastPointData
import com.mizukikk.mltd.extension.date2Millis
import com.mizukikk.mltd.extension.millis2Date
import com.mizukikk.mltd.room.query.IdolItem
import java.util.*

data class LastPoint(
    val lastPointList: List<LastPointData>,
    val anivIdolPointList: List<EventPoint>? = null,
    val anivIdolData: IdolItem? = null
) {
    val hasAnivData get() = anivIdolPointList != null && anivIdolData != null
    val updateDate
        get() :String {
            val data = lastPointList.maxBy { it.summaryTime.date2Millis() }!!
            return data.summaryTime.date2Millis()
                .millis2Date("yyyy/MM/dd HH:mm", TimeZone.getDefault().id)
        }
}