package com.mizukikk.mltd.main.event.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.obj.EventPoint
import com.mizukikk.mltd.api.response.EventResponse
import com.mizukikk.mltd.api.response.GetLastPointResponse
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.main.event.model.Border
import com.mizukikk.mltd.main.event.model.EventBorder
import com.mizukikk.mltd.main.viewmodel.BaseMainViewModel
import com.mizukikk.mltd.room.DBCallBack
import com.mizukikk.mltd.room.query.IdolItem
import retrofit2.Call
import java.util.*

class EventDetailViewModel(application: Application) : BaseMainViewModel(application) {

    val eventBorderLiveData = MutableLiveData<List<EventBorder>>()
    val anivIdolListLiveData = MutableLiveData<List<IdolItem>>()
    private var nextUpdateTime = 0L
    
    fun getEventBorderData(data: EventResponse) {
        if (System.currentTimeMillis() > nextUpdateTime || eventBorderLiveData.value == null) {
            repository.getLastEventPoints(data.id, object : ResponseCallBack<GetLastPointResponse> {
                override fun success(response: GetLastPointResponse) {
                    val eventBorderList = response.getEventBorderList()
                    if (data.isAnivEvent) {
                        getAnivBorder(data, eventBorderList)
                    } else {
                        postEventBorder(eventBorderList)
                    }
                }

                override fun fail(errorMessage: String, errorCode: Int?, call: Call<GetLastPointResponse>) {
                    eventBorderLiveData.postValue(null)
                }
            })
        } else {
            val data = eventBorderLiveData.value!!
            postEventBorder(data)
        }

    }

    private fun getAnivBorder(data: EventResponse, eventBorderList: List<EventBorder>) {
        getAnivEventData(data) { anivEventBorder ->
            if (anivEventBorder != null) {
                val borderList = listOf(anivEventBorder, *eventBorderList.toTypedArray())
                postEventBorder(borderList)
            } else {
                postEventBorder(eventBorderList)
            }
        }
    }

    private fun postEventBorder(eventBorderList: List<EventBorder>) {
        nextUpdateTime = getNextUpdateTime()
        eventBorderLiveData.postValue(eventBorderList)
    }

    private fun getNextUpdateTime() = Calendar.getInstance().apply {
        val min = get(Calendar.MINUTE)
        when (min) {
            in 10..39 -> {
                set(Calendar.MINUTE, 40)
            }
            else -> {
                set(Calendar.MINUTE, 10)
                add(Calendar.HOUR, 1)
            }
        }
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    private fun getAnivEventData(
            data: EventResponse,
            action: (EventBorder?) -> Unit
    ) {
        repository.getAnivIdolRankPoint(data.id, PreferencesHelper.anivIdolId, object : ResponseCallBack<List<EventPoint>> {
            override fun success(response: List<EventPoint>) {
                getAnivIdolData(response, action)
            }

            override fun fail(errorMessage: String, errorCode: Int?, call: Call<List<EventPoint>>) {
                action.invoke(null)
            }
        })
    }

    private fun getAnivIdolData(anivEventPointList: List<EventPoint>, action: (EventBorder?) -> Unit) {
        repository.getAnivIdolIconData(PreferencesHelper.anivIdolId, object : DBCallBack<List<IdolItem>> {
            override fun success(result: List<IdolItem>) {
                if (result.isNotEmpty()) {
                    val idolData = result[0]
                    val title = idolData.idolName
                    var updateDate = anivEventPointList[0].data.maxBy { it.score }!!.updateDate
                    val borderList = anivEventPointList.map {
                        Border(
                                it.rank,
                                it.data.maxBy { it.score.toInt() }!!.score.toInt()
                        )
                    }
                    action.invoke(EventBorder(title, updateDate, borderList, idolData))
                } else {
                    action.invoke(null)
                }
            }

            override fun fail() {
                action.invoke(null)
            }
        })
    }

    fun getAnivIdolList() {
        val anivIdolList = anivIdolListLiveData.value
        if (anivIdolList == null) {
            repository.getAnivEventIdolList(object : DBCallBack<List<IdolItem>> {
                override fun success(result: List<IdolItem>) {
                    anivIdolListLiveData.postValue(result)
                }

                override fun fail() {}
            })
        } else {
            anivIdolListLiveData.postValue(anivIdolList)
        }
    }

    fun changeAnivIDolBorder(data: EventResponse, idolId: Int) {
        val currentIdolId = PreferencesHelper.anivIdolId
        if (currentIdolId != idolId) {
            nextUpdateTime = 0
            PreferencesHelper.anivIdolId = idolId
            getEventBorderData(data)
        }
    }

}