package com.mizukikk.mltd.main.viewmodel

import android.app.Application
import com.mizukikk.mltd.R
import com.mizukikk.mltd.data.Field
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.livedata.SingleLiveEvent

class MainViewModel(application: Application) : BaseMainViewModel(application) {

    val selectLangDialogEvent = SingleLiveEvent<Array<String>>()

    fun getSelectCardLang() =
        if (PreferencesHelper.apiLanguage == Field.API.LANG_JP) {
            getString(R.string.lang_ja)
        } else {
            getString(R.string.lang_zh)
        }

    fun showSelectLangDialog() {
        val langArray = getStringArray(R.array.langs)
        selectLangDialogEvent.postValue(langArray)
    }

    fun saveSelectLang(selectLang: String): Boolean {
        val currentLang = PreferencesHelper.apiLanguage
        val newLang = when (selectLang) {
            getString(R.string.lang_ja) -> Field.API.LANG_JP
            getString(R.string.lang_zh) -> Field.API.LANG_ZH
            else -> Field.API.LANG_JP
        }
        val updateLang = currentLang != newLang
        if (updateLang)
            PreferencesHelper.apiLanguage = newLang
        return updateLang
    }


}