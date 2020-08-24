package com.mizukikk.mltd

import com.mizukikk.mltd.data.MLTDRepository

object Inject {

    fun providerMLTDRepository() = MLTDRepository()

}