package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IdolPoint(
    @Expose
    @SerializedName("borders")
    val borders: List<Int>,
    @Expose
    @SerializedName("idolId")
    val idolId: Int
)