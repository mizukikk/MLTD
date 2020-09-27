package com.mizukikk.mltd.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

@BindingAdapter("smallIcon")
fun loadSmallIcon(imageView: ImageView, imgRes: String?) {
    if (imgRes != null) {
        val url = "https://storage.matsurihi.me/mltd/icon_l/${imgRes}_1.png"
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    } else {
        Glide.with(imageView.context)
            .clear(imageView)
    }
}