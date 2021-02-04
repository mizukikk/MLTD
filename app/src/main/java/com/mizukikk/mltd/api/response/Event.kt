package com.mizukikk.mltd.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mizukikk.mltd.data.model.EventField
import com.mizukikk.mltd.extension.date2Millis
import com.mizukikk.mltd.extension.millis2Date

class Event {
    data class EventResponse(
        @Expose
        @SerializedName("appealType")
        var appealType: Int,
        @Expose
        @SerializedName("id")
        var id: Int,
        @Expose
        @SerializedName("name")
        var name: String,
        @Expose
        @SerializedName("schedule")
        var schedule: Schedule,
        @Expose
        @SerializedName("type")
        var type: Int
    ) {
        val bgUrl get() = EventField.EVENT_IMG_URL_FORMAT.format(id.toString().padStart(4, '0'))
        val date get() = schedule.eventDate
        val sort get() = schedule.beginDate.date2Millis()
    }

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

    data class EventBorders(
        @Expose
        @SerializedName("eventPoint")
        val eventPoint: List<Int>,
        @Expose
        @SerializedName("highScore")
        val highScore: List<Int>,
        @Expose
        @SerializedName("idolPoint")
        val idolPoint: List<IdolPoint>,
        @Expose
        @SerializedName("loungePoint")
        val loungePoint: List<Int>?
    ) {
        val eventPointBorders
            get() = mutableListOf<Int>().apply {
                addAll(listOf(1, 2, 3))
                addAll(eventPoint.filter { it <= 50000 })
            }.joinToString(",")
    }

    data class IdolPoint(
        @Expose
        @SerializedName("borders")
        val borders: List<Int>,
        @Expose
        @SerializedName("idolId")
        val idolId: Int
    )

    class EventPoint : ArrayList<EventPointItem>()

    data class EventPointItem(
        @Expose
        @SerializedName("data")
        val `data`: List<PointData>,
        @Expose
        @SerializedName("rank")
        val rank: Int
    )

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
    )

    data class LastEventPointResponse(
        @Expose
        @SerializedName("eventPoint")
        val eventPoint: EventPoint
    )




}