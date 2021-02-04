package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Score(
    @Expose
    @SerializedName("rank")
    val rank: Int,
    @Expose
    @SerializedName("score")
    val score: Double
)