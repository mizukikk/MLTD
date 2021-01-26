package com.mizukikk.mltd.api

import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.api.response.Event
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    interface CardService {
        @GET("{lan}/cards/")
        fun getAllCard(@Path("lan") lan: String): Call<List<Card.CardResponse>>

        @GET("{lan}/cards/{idolId}")
        fun getCard(
            @Path("lan") lan: String,
            @Path("idolId") idolId: Int
        ): Call<List<Card.CardResponse>>

    }

    interface EventService {
        @GET("events/")
        fun getAllEvent(@Query("prettyPrint") prettyPrint: Boolean = false): Call<List<Event.EventResponse>>
    }
}