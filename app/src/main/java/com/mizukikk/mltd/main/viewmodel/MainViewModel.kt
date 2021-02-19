package com.mizukikk.mltd.main.viewmodel

import android.app.Application
import com.mizukikk.mltd.R
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.EventResponse
import com.mizukikk.mltd.data.Field
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.livedata.SingleLiveEvent
import com.mizukikk.mltd.main.event.model.EventDetailData
import retrofit2.Call

class MainViewModel(application: Application) : BaseMainViewModel(application) {

    val selectLangDialogEvent = SingleLiveEvent<Array<String>>()
    val currentEventEvent = SingleLiveEvent<EventDetailData>()

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

    fun getCurrentEvent() {
        showProgress()
        repository.getEventList(object : ResponseCallBack<List<EventResponse>> {
            override fun success(response: List<EventResponse>) {
                getLastEventPoint(response)
            }

            override fun fail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<EventResponse>>
            ) {
                showToast(getString(R.string.service_error))
            }
        })
    }

    private fun getLastEventPoint(response: List<EventResponse>) {
        try {
            val lastEventData = response.maxBy { it.sort }!!
            val data = EventDetailData(lastEventData)
            currentEventEvent.postValue(data)
            dismissProgress()
        } catch (e: NullPointerException) {
            showToast(getString(R.string.service_error))
        }
    }


}