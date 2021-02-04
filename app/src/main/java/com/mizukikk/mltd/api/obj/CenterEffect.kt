package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CenterEffect(
    @Expose
    @SerializedName("attribute")
    val attribute: Int,
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("idolType.txt")
    val idolType: Int,
    @Expose
    @SerializedName("value")
    val value: Int
)