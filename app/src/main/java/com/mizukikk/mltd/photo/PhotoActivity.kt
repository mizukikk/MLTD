package com.mizukikk.mltd.photo

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.ActivityPhotoBinding
import com.mizukikk.mltd.room.query.IdolItem
import com.yalantis.ucrop.UCrop
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PhotoActivity : AppCompatActivity() {

    companion object {
        private const val PHOTO_URI = "photoUri"
        private const val IDOL_DATA = "idolData"
        fun newIntent(
            activity: Activity,
            photoUri: Uri,
            data: IdolItem
        ) =
            Intent(activity, PhotoActivity::class.java).apply {
                putExtra(PHOTO_URI, photoUri)
                putExtra(IDOL_DATA, data)
            }
    }

    private lateinit var binding: ActivityPhotoBinding
    private lateinit var viewModel: PhotoViewModel
    private var photoUri: Uri? = null
    private var data: IdolItem? = null
    private lateinit var behavior: BottomSheetBehavior<*>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo)
        checkIntentExtra()
        supportPostponeEnterTransition()
        initView()
        initViewModel()
        setListener()
        loadImg()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(PhotoViewModel::class.java)
    }

    private fun initView() {
        behavior = BottomSheetBehavior.from(binding.scrollFunctions)
    }

    private fun checkIntentExtra() {
        photoUri = intent.getParcelableExtra(PHOTO_URI)
        data = intent.getParcelableExtra(IDOL_DATA)
    }

    private fun setListener() {
        binding.ivCard.setOnClickListener {
            when (behavior.state) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        }
        binding.functions.ivSave.setOnClickListener {
            cropPhoto()
        }
    }

    private fun cropPhoto() {
        photoUri?.let {
            val format = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault())
            val fileName = format.format(System.currentTimeMillis())
            UCrop
                .of(it, Uri.fromFile(File(cacheDir, fileName)))
                .withAspectRatio(
                    resources.displayMetrics.widthPixels.toFloat(),
                    resources.displayMetrics.heightPixels.toFloat()
                )
                .start(this)
        }
    }

    private fun loadImg() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            data?.let {
                val resultUri = UCrop.getOutput(data)
                viewModel.savePhoto(resultUri)
            }
        }
    }
}