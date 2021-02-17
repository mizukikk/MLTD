package com.mizukikk.mltd.main.event.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.obj.EventPoint
import com.mizukikk.mltd.api.obj.LastPointData
import com.mizukikk.mltd.api.response.EventResponse
import com.mizukikk.mltd.api.response.GetLastPointResponse
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.main.event.model.LastPoint
import com.mizukikk.mltd.main.viewmodel.BaseMainViewModel
import com.mizukikk.mltd.room.DBCallBack
import com.mizukikk.mltd.room.query.IdolItem
import retrofit2.Call

class EventDetailViewModel(application: Application) : BaseMainViewModel(application) {

    val lastPointLiveData = MutableLiveData<LastPoint>()

    fun getLastEventData(data: EventResponse) {
        repository.getLastEventPoints(data.id, object : ResponseCallBack<GetLastPointResponse> {
            override fun success(response: GetLastPointResponse) {
                if (data.isAnivEvent) {
                    getAnivEventData(data, response.getLastPointList())
                } else {
                    lastPointLiveData.postValue(LastPoint(response.getLastPointList()))
                }
            }

            override fun fail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<GetLastPointResponse>
            ) {
                lastPointLiveData.postValue(null)
            }
        })

    }

    private fun getAnivEventData(data: EventResponse, lastPointList: List<LastPointData>) {
        repository.getAnivIdolRankPoint(
            data.id, PreferencesHelper.anivIdolId,
            object : ResponseCallBack<List<EventPoint>> {
                override fun success(response: List<EventPoint>) {
                    getAnivIdolData(response, lastPointList)
                }

                override fun fail(
                    errorMessage: String,
                    errorCode: Int?,
                    call: Call<List<EventPoint>>
                ) {
                    lastPointLiveData.postValue(LastPoint(lastPointList))
                }
            })
    }

    private fun getAnivIdolData(
        anviIdolPointList: List<EventPoint>,
        lastPointList: List<LastPointData>
    ) {
        repository.getAnivIdolIconData(
            PreferencesHelper.anivIdolId,
            object : DBCallBack<List<IdolItem>> {
                override fun success(result: List<IdolItem>) {
                    if (result.isNotEmpty()) {
                        lastPointLiveData.postValue(
                            LastPoint(
                                lastPointList,
                                anviIdolPointList,
                                result[0]
                            )
                        )
                    } else {
                        lastPointLiveData.postValue(LastPoint(lastPointList))
                    }
                }

                override fun fail() {
                }
            })
    }

}