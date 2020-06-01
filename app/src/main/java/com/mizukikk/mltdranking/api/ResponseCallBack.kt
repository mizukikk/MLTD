package com.mizukikk.mltdranking.api

import retrofit2.Call

abstract class ResponseCallBack<T> {
    abstract fun success(response: T, call: Call<T>)
    abstract fun fail(errorMessage:String, errorCode: Int?,call: Call<T>)
}