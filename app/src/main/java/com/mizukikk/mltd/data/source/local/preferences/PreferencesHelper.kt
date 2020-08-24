package com.mizukikk.mltd.data.source.local.preferences

import android.content.Context.MODE_PRIVATE
import com.mizukikk.mltd.MLTDApplication

object PreferencesHelper {
    private const val MLTD_PREFERNCES_NAME = "mltdPrefernces"

    private const val DEFAULT_API_LANGUAGE = "ja"

    private const val API_LANGUAGE = "apiLanguage"

    private val mltdPrefernces =
        MLTDApplication.applicationContext.getSharedPreferences(MLTD_PREFERNCES_NAME, MODE_PRIVATE)

    var apiLanguage
        get() = mltdPrefernces.getString(
            API_LANGUAGE,
            DEFAULT_API_LANGUAGE
        )!!
        set(value) {
            mltdPrefernces.edit()
                .putString(API_LANGUAGE, value)
                .apply()
        }
}