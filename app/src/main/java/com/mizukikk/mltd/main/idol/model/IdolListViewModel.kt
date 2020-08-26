package com.mizukikk.mltd.main.idol.model

import android.app.Application
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.main.model.BaseMainViewModel
import retrofit2.Call

class IdolListViewModel(application: Application) : BaseMainViewModel(application) {
    private val TAG = IdolListViewModel::class.java.simpleName
    fun getAllCard() {
        repository.getAllCard(object : ResponseCallBack<List<Card.CardResponse>>() {
            override fun success(response: List<Card.CardResponse>) {

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