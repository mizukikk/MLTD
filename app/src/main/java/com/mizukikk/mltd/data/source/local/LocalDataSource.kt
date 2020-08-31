package com.mizukikk.mltd.data.source.local

import com.mizukikk.mltd.room.entity.IdolEntity

interface LocalDataSource {
    fun saveAll(count: () -> Unit, vararg idols: IdolEntity)
}