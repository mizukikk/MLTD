package com.mizukikk.mltdranking.api

import com.mizukikk.mltdranking.BuildConfig
import com.mizukikk.mltdranking.MLTDApplication
import com.mizukikk.mltdranking.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ApiCallBack<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            val body = response.body()!!
            apiSuccess(body, call)
        } else {
            val message = MLTDApplication.applicationContext.getString(R.string.service_error)
            apiFail(message, response.code(), call)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (BuildConfig.DEBUG)
            t.printStackTrace()
        val message = MLTDApplication.applicationContext.getString(R.string.service_error)
        apiFail(message, null, call)
    }

    abstract fun apiSuccess(response: T, call: Call<T>)
    abstract fun apiFail(errorMessage: String, errorCode: Int?, call: Call<T>)

}