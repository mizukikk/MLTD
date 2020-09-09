package com.mizukikk.mltd.api

import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.mizukikk.mltd.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {

    private const val API_TIME_OUT = 120L
    private const val API_URL = "https://api.matsurihi.me/mltd/v1/"
    private val gson by lazy {
        GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create()

    }
    private val okHttpClient by lazy {
        OkHttpClient.Builder().apply {
            connectTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            readTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                addNetworkInterceptor(StethoInterceptor())
            }
        }.build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    val instance get() = retrofit


}