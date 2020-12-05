package com.mizukikk.mltd.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.mizukikk.mltd.main.idol.model.CenterEffect
import java.lang.reflect.Type

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

    fun <T> toList(json: String, typeOfT: Type): T =
        gson.fromJson(json, typeOfT)
}