package com.mizukikk.mltd.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mizukikk.mltd.api.obj.Schedule
import com.mizukikk.mltd.data.model.EventField
import com.mizukikk.mltd.extension.date2Millis

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
