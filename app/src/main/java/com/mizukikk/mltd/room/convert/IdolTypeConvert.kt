package com.mizukikk.mltd.room.convert

import android.content.res.TypedArray
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mizukikk.mltd.room.entity.*
import java.lang.Exception

class IdolTypeConvert {

    @TypeConverter
    fun listToJson(list: List<Skill>?) = try {
        if (list == null)
            null
        else
            Gson().toJson(list)
    } catch (e: Exception) {
        null
    }

    @TypeConverter
    fun jsonArrayToList(jsonArray: String?) = try {
        if (jsonArray == null)
            null
        else
            Gson().fromJson<List<Skill>>(jsonArray, object : TypeToken<List<Skill>>() {}.type)
    } catch (e: Exception) {
        null
    }

    @TypeConverter
    fun objToJson(obj: Any?) = try {
        if (obj == null)
            null
        else
            Gson().toJson(obj)
    } catch (e: Exception) {
        null
    }

    @TypeConverter
    fun jsonToBonusCostume(json: String?) = try {
        if (json == null)
            null
        else
            Gson().fromJson(json, BonusCostume::class.java)
    } catch (e: Exception) {
        null
    }


    @TypeConverter
    fun jsonToCenterEffect(json: String?) = try {
        if (json == null)
            null
        else
            Gson().fromJson(json, CenterEffect::class.java)
    } catch (e: Exception) {
        null
    }


    @TypeConverter
    fun jsonToCostume(json: String?) = try {
        if (json == null)
            null
        else
            Gson().fromJson(json, Costume::class.java)
    } catch (e: Exception) {
        null
    }


    @TypeConverter
    fun jsonToRank5Costume(json: String?) = try {
        if (json == null)
            null
        else
            Gson().fromJson(json, Rank5Costume::class.java)
    } catch (e: Exception) {
        null
    }

}