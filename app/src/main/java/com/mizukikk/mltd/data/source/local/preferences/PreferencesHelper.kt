package com.mizukikk.mltd.data.source.local.preferences

import android.content.Context.MODE_PRIVATE
import com.mizukikk.mltd.MLTDApplication

object PreferencesHelper {
    private const val MLTD_PREFERNCES_NAME = "mltdPrefernces"

    private const val DEFAULT_API_LANGUAGE = "ja"

    private const val API_LANGUAGE = "apiLanguage"
    private const val NEXT_CHECK_DB_TIME = "nextCheckDBTime"

    private val mltdPreferences =
        MLTDApplication.applicationContext.getSharedPreferences(MLTD_PREFERNCES_NAME, MODE_PRIVATE)

    var apiLanguage
        get() = mltdPreferences.getString(
            API_LANGUAGE,
            DEFAULT_API_LANGUAGE
        )!!
        set(value) {
            mltdPreferences.edit()
                .putString(API_LANGUAGE, value)
                .apply()
        }

    var nextUpdateTimeMillis
        get() = mltdPreferences.getLong(NEXT_CHECK_DB_TIME, 0L)
        set(value) = mltdPreferences.edit().putLong(NEXT_CHECK_DB_TIME, value).apply()
}