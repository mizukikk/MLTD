package com.mizukikk.mltdranking.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mizukikk.mltdranking.BuildConfig
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
        val builder = OkHttpClient.Builder()
            .connectTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(API_TIME_OUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG)
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        builder.build()
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