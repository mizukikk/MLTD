package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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
