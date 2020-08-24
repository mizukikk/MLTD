package com.mizukikk.mltd.api

import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.api.response.Event
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    interface CardService {
        @GET("{lan}/cards/")
        fun getAllCard(@Path("lan") lan: String): Call<List<Card.CardResponse>>
    }

    interface EventService {
        @GET("{lan}/events/")
        fun getAllEvent(@Path("lan") lan: String): Call<List<Event.EventResponse>>
    }
}