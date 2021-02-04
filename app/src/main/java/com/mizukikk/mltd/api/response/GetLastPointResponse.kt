package com.mizukikk.mltd.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mizukikk.mltd.api.obj.LastPointData


data class GetLastPointResponse(
    @Expose
    @SerializedName("eventPoint")
    val eventPoint: LastPointData
)



