package com.mizukikk.mltd.main.event.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.obj.LastPointData
import com.mizukikk.mltd.api.response.GetLastPointResponse
import com.mizukikk.mltd.main.viewmodel.BaseMainViewModel
import retrofit2.Call

class EventDetailViewModel(application: Application) : BaseMainViewModel(application) {

    val lastPointListLiveData = MutableLiveData<List<LastPointData>>()

    fun getLastEventData(id: Int) {
        repository.getLastEventPoints(id, object : ResponseCallBack<GetLastPointResponse> {
            override fun success(response: GetLastPointResponse) {
                lastPointListLiveData.postValue(response.getLastPointList())
            }

            override fun fail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<GetLastPointResponse>
            ) {
                lastPointListLiveData.postValue(null)
            }
        })

    }

}