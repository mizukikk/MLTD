package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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
