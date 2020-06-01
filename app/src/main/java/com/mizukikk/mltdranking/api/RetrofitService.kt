package com.mizukikk.mltdranking.api

import com.mizukikk.mltdranking.api.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {


    interface Event {
        @GET("events/")
        fun getAllEvent(): Call<List<EventResponse>>
    }
}