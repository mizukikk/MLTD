package com.mizukikk.mltd.room.convert

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mizukikk.mltd.room.entity.*
import java.lang.Exception

class IdolTypeConvert {


    @TypeConverter
    fun listToJson(list: List<Any>?) = try {
        if (list == null)
            null
        else
            Gson().toJson(list)
    } catch (e: Exception) {
        null
    }

    @TypeConverter
    fun intJsonArrayToList(jsonArray: String?) = try {
        if (jsonArray == null)
            null
        else
            Gson().fromJson<List<Int>>(
                jsonArray,
                object : TypeToken<List<Int>>() {}.type
            )
    } catch (e: Exception) {
        null
    }

    @TypeConverter
    fun jsonArrayToList(jsonArray: String?) = try {
        if (jsonArray == null)
            null
        else
            Gson().fromJson<List<SkillEntity>>(
                jsonArray,
                object : TypeToken<List<SkillEntity>>() {}.type
            )
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
            Gson().fromJson(json, BonusCostumeEntity::class.java)
    } catch (e: Exception) {
        null
    }


    @TypeConverter
    fun jsonToCenterEffect(json: String?) = try {
        if (json == null)
            null
        else
            Gson().fromJson(json, CenterEffectEntity::class.java)
    } catch (e: Exception) {
        null
    }


    @TypeConverter
    fun jsonToCostume(json: String?) = try {
        if (json == null)
            null
        else
            Gson().fromJson(json, CostumeEntity::class.java)
    } catch (e: Exception) {
        null
    }


    @TypeConverter
    fun jsonToRank5Costume(json: String?) = try {
        if (json == null)
            null
        else
            Gson().fromJson(json, Rank5CostumeEntity::class.java)
    } catch (e: Exception) {
        null
    }

}