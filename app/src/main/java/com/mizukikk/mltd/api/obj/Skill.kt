package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Skill(
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("duration")
    val duration: Int,
    @Expose
    @SerializedName("effectId")
    val effectId: Int,
    @Expose
    @SerializedName("evaluation")
    val evaluation: Int,
    @Expose
    @SerializedName("evaluation2")
    val evaluation2: Int,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("interval")
    val interval: Int,
    @Expose
    @SerializedName("probability")
    val probability: Int,
    @Expose
    @SerializedName("value")
    val value: List<Int>
)