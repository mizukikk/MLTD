package com.mizukikk.mltd.photo

import android.app.Application
import android.net.Uri
import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.livedata.SingleLiveEvent
import com.mizukikk.mltd.main.model.BaseMainViewModel
import java.io.File

class PhotoViewModel(application: Application) : BaseMainViewModel(application) {

    val savePhotoEvent = SingleLiveEvent<Boolean>()


    fun savePhoto(resultUri: Uri?) {
    }

}