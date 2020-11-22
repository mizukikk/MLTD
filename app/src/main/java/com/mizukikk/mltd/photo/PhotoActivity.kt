package com.mizukikk.mltd.photo

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.ActivityPhotoBinding

class PhotoActivity : AppCompatActivity() {

    companion object {
        private const val PHOTO_URI = "photoUri"
        fun newIntent(activity: Activity, photoUri: Uri) =
            Intent(activity, PhotoActivity::class.java).apply {
                putExtra(PHOTO_URI, photoUri)
            }
    }

    private lateinit var binding: ActivityPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo)
        supportPostponeEnterTransition()
        loadImg()
    }

    private fun loadImg() {
        val photoUri = intent.getParcelableExtra<Uri>(PHOTO_URI)!!
        Glide.with(this)
            .load(photoUri)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition()
                    return false
                }
            })
            .into(binding.ivCard)
    }
}