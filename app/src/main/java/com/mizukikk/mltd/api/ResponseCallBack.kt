package com.mizukikk.mltd.api

import retrofit2.Call

interface ResponseCallBack<T> {
    fun success(response: T)
    fun fail(errorMessage: String, errorCode: Int?, call: Call<T>)
}