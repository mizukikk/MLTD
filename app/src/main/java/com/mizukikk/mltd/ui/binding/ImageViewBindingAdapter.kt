package com.mizukikk.mltd.ui.binding

import android.graphics.Color
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.mizukikk.mltd.R
import com.mizukikk.mltd.data.Field
import com.mizukikk.mltd.extension.convertDp2Px
import com.mizukikk.mltd.room.entity.IdolEntity
import com.mizukikk.mltd.room.query.IdolItem

@BindingAdapter("smallIcon")
fun loadSmallIcon(imageView: ImageView, data: IdolEntity?) {
    if (data != null) {
        val url = "https://storage.matsurihi.me/mltd/icon_l/${data.resourceId}_0.png"
        val placeHolder = when (data.idolType) {
            Field.IdolType.PRINCESS -> R.color.princess
            Field.IdolType.FAIRY -> R.color.fairy
            Field.IdolType.ANGEL -> R.color.angle
            Field.IdolType.EX -> android.R.color.transparent
            else -> Color.TRANSPARENT
        }
        Glide.with(imageView.context)
            .load(url)
            .placeholder(placeHolder)
            .into(imageView)
    } else {
        Glide.with(imageView.context)
            .clear(imageView)
    }
}

@BindingAdapter("rarityBorder")
fun setIconRarityBorder(imageView: ImageView, data: IdolEntity?) {
    if (data != null) {
        val borderRes = when (data.rarity) {
            Field.Rarity.N -> android.R.color.transparent
            Field.Rarity.R -> R.drawable.ic_r_border
            Field.Rarity.SR -> R.drawable.ic_sr_border
            Field.Rarity.SSR -> R.drawable.ic_ssr_border
            else -> Color.TRANSPARENT
        }
        Glide.with(imageView.context)
            .load(borderRes)
            .into(imageView)
    } else {
        Glide.with(imageView.context)
            .clear(imageView)
    }
}

@BindingAdapter("idolType")
fun setIconIdolType(imageView: ImageView, idolType: Int?) {
    val iconRes = when (idolType) {
        Field.IdolType.PRINCESS -> R.drawable.ic_princess
        Field.IdolType.FAIRY -> R.drawable.ic_fairy
        Field.IdolType.ANGEL -> R.drawable.ic_angle
        Field.IdolType.EX -> R.drawable.ic_ex
        else -> android.R.color.transparent
    }
    Glide.with(imageView.context)
        .load(iconRes)
        .into(imageView)
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