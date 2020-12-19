package com.mizukikk.mltd.main.idol.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.data.model.IdolField
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.extension.nextUpdateTimeMillis
import com.mizukikk.mltd.livedata.SingleLiveEvent
import com.mizukikk.mltd.main.idol.adapter.FilterIdolManager
import com.mizukikk.mltd.main.model.BaseMainViewModel
import com.mizukikk.mltd.room.DBCallBack
import com.mizukikk.mltd.room.entity.IdolEntity
import com.mizukikk.mltd.room.query.IdolItem
import com.mizukikk.mltd.utils.GsonUtils
import retrofit2.Call

class IdolListViewModel(application: Application) : BaseMainViewModel(application) {

    private val TAG = IdolListViewModel::class.java.simpleName
    val idolListLiveData = MutableLiveData<List<IdolItem>>()
    val filterIdolManagerLiveData = MutableLiveData<FilterIdolManager>()
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

    fun getIdolListItem() {
        repository.getIdolList(
            lastIdolId,
            PreferencesHelper.apiLanguage,
            object : DBCallBack<List<IdolItem>> {
                override fun success(result: List<IdolItem>) {
                    val newIdolList = mutableListOf<IdolItem>()
                    newIdolList.addAll(result)
                    idolListLiveData.postValue(newIdolList)
                    val nextUpdateMillis = PreferencesHelper.nextUpdateTimeMillis
                    val updateData = System.currentTimeMillis() >= nextUpdateMillis
                    val updateResult = IdolListResult.updateIdolData(updateData, lastIdolId)
                    idolListEvent.postValue(updateResult)
                }

                override fun fail() {
                    //do noting
                }
            })
    }

    private fun saveNextUpdateTimeMillis() {
        PreferencesHelper.nextUpdateTimeMillis =
            System.currentTimeMillis().nextUpdateTimeMillis()
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
            if (maxProgress == progress)
                saveNextUpdateTimeMillis()
            val result = IdolListResult.setSaveDataProgress(progress, maxProgress)
            idolListEvent.postValue(result)
        }, *response.toTypedArray())
    }

    fun refreshData() {
        getIdolListItem()
    }

    fun getFilterData(update: Boolean = false) {
        if (filterIdolManagerLiveData.value == null || update) {
            val centerEffect = getAssetsDataText(IdolField.FilePath.CENTER_EFFECT)
            val extraType = getAssetsDataText(IdolField.FilePath.EXTRA_TYPE)
            val idolType = getAssetsDataText(IdolField.FilePath.IDOL_TYPE)
            val rarity = getAssetsDataText(IdolField.FilePath.RARITY)
            val skill = getAssetsDataText(IdolField.FilePath.SKILL)
            val centerEffectList = GsonUtils.toList<List<CenterEffect>>(
                centerEffect,
                object : TypeToken<List<CenterEffect>>() {}.type
            ).filter { it.centerEffectValue != null }
            val extraTypeList = GsonUtils.toList<List<ExtraType>>(
                extraType,
                object : TypeToken<List<ExtraType>>() {}.type
            ).filter { it.extraTypeValue != null }
            val idolTypeList =
                GsonUtils.toList<List<IdolType>>(
                    idolType,
                    object : TypeToken<List<IdolType>>() {}.type
                )
            val rarityList =
                GsonUtils.toList<List<Rarity>>(rarity, object : TypeToken<List<Rarity>>() {}.type)
            val skillList =
                GsonUtils.toList<List<Skill>>(skill, object : TypeToken<List<Skill>>() {}.type)
                    .filter { it.skillValue != null }
            val filterIdolManager =
                FilterIdolManager(
                    centerEffectList,
                    extraTypeList,
                    idolTypeList,
                    rarityList,
                    skillList
                )
            filterIdolManagerLiveData.postValue(filterIdolManager)
        }
    }

    fun reloadData() {
        getFilterData(true)
        checkDBdData()
    }
}