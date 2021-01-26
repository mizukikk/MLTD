package com.mizukikk.mltd.picture

import android.app.Application
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.livedata.SingleLiveEvent
import com.mizukikk.mltd.main.viewmodel.BaseMainViewModel
import com.mizukikk.mltd.utils.FileUtils
import java.io.File
import java.lang.Exception

class PhotoViewModel(application: Application) : BaseMainViewModel(application) {

    val savePhotoResultEvent = SingleLiveEvent<Boolean>()


    fun savePhoto(resultUri: Uri?, fileName: String) {
        Glide.with(MLTDApplication.appContext)
            .load(resultUri)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    savePhotoResultEvent.postValue(false)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    AsyncTask.execute {
                        try {
                            val imgBitmap = (resource as BitmapDrawable).bitmap
                            FileUtils.saveImage(imgBitmap, fileName)
                            savePhotoResultEvent.postValue(true)
                        } catch (e: Exception) {
                            savePhotoResultEvent.postValue(false)
                        }
                    }
                    return false
                }
            })
            .submit()
    }

    fun getCropCacheDir(): String {
        val cropCacheDir = "${MLTDApplication.appContext.cacheDir}/crop"
        val file = File(cropCacheDir)
        if (file.exists().not())
            file.mkdir()
        return cropCacheDir
    }

}