package com.mizukikk.mltd.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mizukikk.mltd.api.obj.Score


data class GetLastPointResponse(
    @Expose
    @SerializedName("eventPoint")
    val eventPoint: EventPoint
)

data class EventPoint(
    @Expose
    @SerializedName("count")
    val count: Int,
    @Expose
    @SerializedName("scores")
    val scores: List<Score>,
    @Expose
    @SerializedName("summaryTime")
    val summaryTime: String
)


