package com.mizukikk.mltd.main.idol.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.extension.nextUpdateTimeMillis
import com.mizukikk.mltd.livedata.SingleLiveEvent
import com.mizukikk.mltd.main.model.BaseMainViewModel
import com.mizukikk.mltd.room.DBCallBack
import com.mizukikk.mltd.room.entity.IdolEntity
import com.mizukikk.mltd.room.query.IdolItem
import retrofit2.Call

class IdolListViewModel(application: Application) : BaseMainViewModel(application) {

    private val TAG = IdolListViewModel::class.java.simpleName
    val idolListLiveData = MutableLiveData<List<IdolItem>>()
    val idolListEvent = SingleLiveEvent<IdolListResult>()

    private var lastIdolId = -1

    fun checkDBdData() {
        repository.checkDBData(object : DBCallBack<List<IdolEntity>> {
            override fun success(result: List<IdolEntity>) {
                if (result.isNotEmpty())
                    lastIdolId = result[0].id
                idolListEvent.postValue(IdolListResult.dbDataEmpty(result.isEmpty()))
            }

            override fun fail() {
                idolListEvent.postValue(IdolListResult.dbDataEmpty(true))
            }
        })
    }

    fun getFirstListItem() {
        repository.getIdolList(
            lastIdolId,
            PreferencesHelper.apiLanguage,
            object : DBCallBack<List<IdolItem>> {
                override fun success(result: List<IdolItem>) {
                    val newIdolList = mutableListOf<IdolItem>()
                    newIdolList.addAll(result)
                    idolListLiveData.postValue(newIdolList)
                    val nextUpdateMillis = PreferencesHelper.nextUpdateTimeMillis
                    if (System.currentTimeMillis() >= nextUpdateMillis)
                        checkUpdate()
                }

                override fun fail() {
                    //do noting
                }
            })
    }

    private fun checkUpdate() {
        repository.checkUpdate(lastIdolId, object : ResponseCallBack<List<Card.CardResponse>>() {
            override fun success(response: List<Card.CardResponse>) {
                val update = response.isNotEmpty()
                if (update) {
                    updateDB()
                }
            }

            override fun fail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<Card.CardResponse>>
            ) {
                //do noting
            }
        })
    }

    private fun updateDB() {
        repository.downloadAllCard(object : ResponseCallBack<List<Card.CardResponse>>() {
            override fun success(response: List<Card.CardResponse>) {
                val finishProgress = response.size
                repository.saveAll({ progress ->
                    if (finishProgress == progress) {
                        PreferencesHelper.nextUpdateTimeMillis =
                            System.currentTimeMillis().nextUpdateTimeMillis()
                    }
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

    fun getNextCardListItem(lastCardId: Int) {
        repository.getIdolList(
            lastCardId,
            PreferencesHelper.apiLanguage,
            object : DBCallBack<List<IdolItem>> {
                override fun success(result: List<IdolItem>) {
                    val newIdolList = mutableListOf<IdolItem>()
                    val idolList = idolListLiveData.value
                    if (idolList != null)
                        newIdolList.addAll(idolList)
                    newIdolList.addAll(result)
                    idolListLiveData.postValue(newIdolList)
                }

                override fun fail() {
                    //do noting
                }
            })
    }

    fun downloadAllCard() {
        repository.downloadAllCard(object : ResponseCallBack<List<Card.CardResponse>>() {
            override fun success(response: List<Card.CardResponse>) {
                idolListEvent.postValue(IdolListResult.downloadResult(true, response.size))
                saveIdolData(response)
            }

            override fun fail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<Card.CardResponse>>
            ) {
                idolListEvent.postValue(IdolListResult.downloadResult(false))
            }
        })
    }

    private fun saveIdolData(response: List<Card.CardResponse>) {
        val maxProgress = response.size
        repository.saveAll({ progress ->
            val result = IdolListResult.setSaveDataProgress(progress, maxProgress)
            idolListEvent.postValue(result)
        }, *response.toTypedArray())
    }
}