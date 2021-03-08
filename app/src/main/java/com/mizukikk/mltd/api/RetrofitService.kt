package com.mizukikk.mltd.api

import com.mizukikk.mltd.api.obj.EventBorders
import com.mizukikk.mltd.api.obj.EventPoint
import com.mizukikk.mltd.api.response.CardResponse
import com.mizukikk.mltd.api.response.EventResponse
import com.mizukikk.mltd.api.response.GetLastPointResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    interface CardService {
        @GET("{lan}/cards/")
        fun getAllCard(@Path("lan") lan: String): Call<List<CardResponse>>

        @GET("{lan}/cards/{idolId}")
        fun getCard(@Path("lan") lan: String, @Path("idolId") idolId: Int): Call<List<CardResponse>>

    }

    interface EventService {
        @GET("events/")
        fun getAllEvent(@Query("prettyPrint") prettyPrint: Boolean = false): Call<List<EventResponse>>

        @GET("events/{id}/rankings/borders")
        fun getEventBorders(@Path("id") id: Int): Call<EventBorders>

        @GET("events/{id}/rankings/borderPoints")
        fun getLastEventPoints(@Path("id") id: Int, @Query("prettyPrint") prettyPrint: Boolean = false): Call<GetLastPointResponse>

        @GET("events/{id}/rankings/logs/eventPoint/{borders}")
        fun getEventPoint(@Path("id") id: Int, @Path("borders") borders: String): Call<EventPoint>

        @GET("events/{id}/rankings/logs/{type}/{ranks}")
        fun getEventRankLog(
                @Path("id") id: Int,
                @Path("type") type: String,
                @Path("ranks") ranks: String,
                @Query("prettyPrint") prettyPrint: Boolean = false
        ): Call<List<EventPoint>>

        @GET("events/{id}/rankings/logs/idolPoint/{idolId}/1,2,3,10,100,1000")
        fun getAnivIdolRankPoint(
                @Path("id") id: Int,
                @Path("idolId") idolId: Int,
                @Query("prettyPrint") prettyPrint: Boolean = false
        ): Call<List<EventPoint>>
    }
}