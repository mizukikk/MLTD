package com.mizukikk.mltd.api.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mizukikk.mltd.api.obj.Schedule
import com.mizukikk.mltd.data.model.EventField
import com.mizukikk.mltd.extension.date2Millis
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventResponse(
    @Expose
    @SerializedName("appealType")
    val appealType: Int,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("schedule")
    val schedule: Schedule,
    @Expose
    @SerializedName("type")
    val type: Int
) : Parcelable {
    val bgUrl get() = EventField.EVENT_IMG_URL_FORMAT.format(id.toString().padStart(4, '0'))
    val date get() = schedule.eventDate
    val sort get() = schedule.beginDate.date2Millis()
    val isAnivEvent get() = type == EventField.Type.ANNIVERSARY
}
