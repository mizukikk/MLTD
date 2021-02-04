package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LastPointData(
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