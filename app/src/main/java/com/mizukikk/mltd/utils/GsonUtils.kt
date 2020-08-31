package com.mizukikk.mltd.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

object GsonUtils {


    private val gson by lazy {
        Gson()
    }

    fun toJsonString(jsonObj: Any) = gson.toJson(jsonObj)
    fun <T> toDataObj(json: String, clazz: Class<T>): T? =
        try {
            gson.fromJson(json, clazz)
        } catch (e: JsonSyntaxException) {
            null
        }
}