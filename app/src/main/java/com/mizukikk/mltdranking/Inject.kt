package com.mizukikk.mltdranking

import com.mizukikk.mltdranking.data.MLTDRepository

object Inject {

    fun providerMLTDRepository() = MLTDRepository()

}