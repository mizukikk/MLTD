package com.mizukikk.mltd.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

@BindingAdapter("smallIcon")
fun loadSmallIcon(imageView: ImageView, imgRes: String) {
    val url = "https://storage.matsurihi.me/mltd/icon_l/${imgRes}_1.png"
    Glide.with(imageView.context)
        .load(url)
        .transition(withCrossFade())
        .into(imageView)
}