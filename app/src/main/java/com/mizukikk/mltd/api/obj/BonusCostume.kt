package com.mizukikk.mltd.api.obj

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BonusCostume(
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("modelId")
    val modelId: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("resourceId")
    val resourceId: String,
    @Expose
    @SerializedName("sortId")
    val sortId: Int
)