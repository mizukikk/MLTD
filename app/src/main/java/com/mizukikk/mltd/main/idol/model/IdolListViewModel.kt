package com.mizukikk.mltd.main.idol.model

import android.app.Application
import android.util.Log
import com.google.gson.GsonBuilder
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.main.model.BaseMainViewModel
import com.mizukikk.mltd.room.DBCallBack
import com.mizukikk.mltd.room.entity.IdolEntity
import com.mizukikk.mltd.room.query.IdolItem
import retrofit2.Call

class IdolListViewModel(application: Application) : BaseMainViewModel(application) {
    private val TAG = IdolListViewModel::class.java.simpleName

    fun getCardList() {
        repository.getIdolList(
            0,
            PreferencesHelper.apiLanguage,
            object : DBCallBack<List<IdolItem>> {
                override fun success(result: List<IdolItem>) {
                }

                override fun fail() {

                }
            })

    }

    fun getAllCard() {
        repository.downloadAllCard(object : ResponseCallBack<List<Card.CardResponse>>() {
            override fun success(response: List<Card.CardResponse>) {
                repository.saveAll({
                    Log.d(TAG, "success: aaa3 $it")
                }, *response.toTypedArray())
            }

            override fun fail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<Card.CardResponse>>
            ) {

            }
        })
    }
}