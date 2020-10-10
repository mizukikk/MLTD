package com.mizukikk.mltd.ui.binding

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.mizukikk.mltd.R
import com.mizukikk.mltd.extension.convertDp2Px
import com.mizukikk.mltd.room.entity.IdolEntity

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

@BindingAdapter("cardBG")
fun loadCardBG(imageView: ImageView, cardBGURl: String?) {
    if (cardBGURl != null) {
        val progress = CircularProgressDrawable(imageView.context).apply {
            strokeWidth = 5f.convertDp2Px()
            centerRadius = 30f.convertDp2Px()
            setColorSchemeColors(ContextCompat.getColor(imageView.context, R.color.colorPrimary))
            start()
        }
        Glide.with(imageView.context)
            .load(cardBGURl)
            .error(R.color.white)
            .transition(withCrossFade())
            .placeholder(progress)
            .into(imageView)
    } else {
        Glide.with(imageView.context)
            .clear(imageView)
    }
}