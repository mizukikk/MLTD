package com.mizukikk.mltd.data.source.local.preferences

import android.content.Context.MODE_PRIVATE
import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.data.Field

object PreferencesHelper {
    private const val MLTD_PREFERNCES_NAME = "mltdPrefernces"

    private const val API_LANGUAGE = "apiLanguage"
    private const val ANIV_IDOL_ID = "anivIdolId"
    private val NEXT_CHECK_DB_TIME get() = "nextCheckDBTime_$apiLanguage"

    private val mltdPreferences =
        MLTDApplication.appContext.getSharedPreferences(MLTD_PREFERNCES_NAME, MODE_PRIVATE)

    var apiLanguage
        get() = mltdPreferences.getString(
            API_LANGUAGE,
            Field.API.LANG_JP
        )!!
        set(value) {
            mltdPreferences.edit()
                .putString(API_LANGUAGE, value)
                .apply()
        }

    var anivIdolId
        get() = mltdPreferences.getInt(ANIV_IDOL_ID, 1)
        set(value) {
            mltdPreferences.edit()
                .putInt(ANIV_IDOL_ID, value)
                .apply()
        }

    var nextUpdateTimeMillis
        get() = mltdPreferences.getLong(NEXT_CHECK_DB_TIME, 0L)
        set(value) = mltdPreferences.edit().putLong(NEXT_CHECK_DB_TIME, value).apply()
}