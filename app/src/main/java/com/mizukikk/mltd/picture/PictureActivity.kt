package com.mizukikk.mltd.picture

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.ActivityPictureBinding
import com.mizukikk.mltd.room.query.IdolItem
import com.yalantis.ucrop.UCrop
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PictureActivity : AppCompatActivity() {

    companion object {
        private const val PHOTO_URI = "photoUri"
        private const val IDOL_DATA = "idolData"
        private const val RC_SAVE_PICTURE = 1111
        fun newIntent(
            activity: Activity,
            photoUri: Uri,
            data: IdolItem
        ) =
            Intent(activity, PictureActivity::class.java).apply {
                putExtra(PHOTO_URI, photoUri)
                putExtra(IDOL_DATA, data)
            }
    }

    object SavePhotoSize {
        const val ORIGIN_SIZE = 0
        const val PHONE_SIZE = 1
    }

    private lateinit var binding: ActivityPictureBinding
    private lateinit var viewModel: PhotoViewModel
    private var photoUri: Uri? = null
    private var data: IdolItem? = null
    private lateinit var behavior: BottomSheetBehavior<*>
    private val savePhotoSizeArray by lazy { resources.getStringArray(R.array.savePhotoSize) }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_picture)
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
        viewModel.savePhotoResultEvent.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, getString(R.string.toast_save_pic_success), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, getString(R.string.toast_save_pic_fail), Toast.LENGTH_SHORT)
                    .show()
            }
        })
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
            if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                savePicture()
            } else {
                requestPermission(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    RC_SAVE_PICTURE
                )
            }
        }
    }

    private fun savePicture() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.activity_picture_select_pic_size))
            .setAdapter(
                ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    savePhotoSizeArray
                )
            ) { dialog, which ->
                when (which) {
                    SavePhotoSize.ORIGIN_SIZE -> viewModel.savePhoto(photoUri, data!!.idol.name)
                    SavePhotoSize.PHONE_SIZE -> cropPhoto()
                }
            }
            .show()
    }

    private fun requestPermission(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    private fun checkPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun cropPhoto() {
        photoUri?.let {
            val format = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault())
            val fileName = format.format(System.currentTimeMillis())
            val parent = viewModel.getCropCacheDir()
            UCrop
                .of(it, Uri.fromFile(File(parent, fileName)))
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
        when (requestCode) {
            UCrop.REQUEST_CROP -> {
                if (resultCode == RESULT_OK) {
                    data?.let {
                        val resultUri = UCrop.getOutput(data)
                        viewModel.savePhoto(resultUri, this.data!!.idol.name)
                    }
                } else {
                    viewModel.savePhotoResultEvent.postValue(false)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val isGrant =
            grantResults.isNotEmpty()
                    && grantResults.contains(PackageManager.PERMISSION_DENIED).not()
        if (isGrant)
            when (requestCode) {
                RC_SAVE_PICTURE -> {
                    savePicture()
                }
            }
    }
}