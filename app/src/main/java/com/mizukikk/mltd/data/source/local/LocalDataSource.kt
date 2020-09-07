package com.mizukikk.mltd.data.source.local

import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.room.entity.IdolEntity

interface LocalDataSource {
    fun saveAll(count: (progress: Int) -> Unit, vararg cards: Card.CardResponse)
}