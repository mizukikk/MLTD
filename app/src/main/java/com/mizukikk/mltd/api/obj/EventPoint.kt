package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EventPoint(
    @Expose
    @SerializedName("data")
    val `data`: List<PointData>,
    @Expose
    @SerializedName("rank")
    val rank: Int
)
