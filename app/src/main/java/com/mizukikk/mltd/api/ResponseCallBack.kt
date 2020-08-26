package com.mizukikk.mltd.api

import retrofit2.Call

abstract class ResponseCallBack<T> {
    abstract fun success(response: T)
    abstract fun fail(errorMessage:String, errorCode: Int?,call: Call<T>)
}