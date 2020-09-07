package com.mizukikk.mltd.main.idol.model

import android.app.Application
import android.util.Log
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.main.model.BaseMainViewModel
import com.mizukikk.mltd.room.entity.IdolEntity
import retrofit2.Call

class IdolListViewModel(application: Application) : BaseMainViewModel(application) {
    private val TAG = IdolListViewModel::class.java.simpleName
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