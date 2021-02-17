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

class EventDetailViewModel(application: Application) : BaseMainViewModel(application) {

    val eventBorderLiveData = MutableLiveData<List<EventBorder>>()

    fun getEventBorderData(data: EventResponse) {
        repository.getLastEventPoints(data.id, object : ResponseCallBack<GetLastPointResponse> {
            override fun success(response: GetLastPointResponse) {
                val eventBorderList = response.getEventBorderList()
                if (data.isAnivEvent) {
                    getAnivEventData(data) {
                        if (it != null) {
                            eventBorderLiveData.postValue(
                                listOf(it, *eventBorderList.toTypedArray())
                            )
                        } else {
                            eventBorderLiveData.postValue(eventBorderList)
                        }
                    }
                } else {
                    eventBorderLiveData.postValue(eventBorderList)
                }
            }

            override fun fail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<GetLastPointResponse>
            ) {
                eventBorderLiveData.postValue(null)
            }
        })

    }

    private fun getAnivEventData(
        data: EventResponse,
        action: (EventBorder?) -> Unit
    ) {
        repository.getAnivIdolRankPoint(
            data.id, PreferencesHelper.anivIdolId,
            object : ResponseCallBack<List<EventPoint>> {
                override fun success(response: List<EventPoint>) {
                    getAnivIdolData(response, action)
                }

                override fun fail(
                    errorMessage: String,
                    errorCode: Int?,
                    call: Call<List<EventPoint>>
                ) {
                    action.invoke(null)
                }
            })
    }

    private fun getAnivIdolData(
        anivEventPointList: List<EventPoint>,
        action: (EventBorder?) -> Unit
    ) {
        repository.getAnivIdolIconData(
            PreferencesHelper.anivIdolId,
            object : DBCallBack<List<IdolItem>> {
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

}