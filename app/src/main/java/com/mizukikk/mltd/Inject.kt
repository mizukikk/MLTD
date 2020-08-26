package com.mizukikk.mltd

import com.mizukikk.mltd.data.MLTDRepository
import com.mizukikk.mltd.data.source.remote.MLTDRemoteDataSource

object Inject {

    fun providerMLTDRepository() =
        MLTDRepository.newInstance(MLTDRemoteDataSource)

}