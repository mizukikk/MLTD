package com.mizukikk.mltd.data.source.remote

import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Card

interface RemoteDataSource {
    fun downloadAllCard(callBack: ResponseCallBack<List<Card.CardResponse>>)
}