package com.mizukikk.mltd.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


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
)

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
)