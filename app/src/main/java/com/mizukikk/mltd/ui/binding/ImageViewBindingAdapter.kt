package com.mizukikk.mltd.ui.binding

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.mizukikk.mltd.R
import com.mizukikk.mltd.data.model.IdolField
import com.mizukikk.mltd.extension.convertDp2Px
import com.mizukikk.mltd.room.entity.IdolEntity

@BindingAdapter("smallIcon")
fun loadSmallIcon(imageView: ImageView, data: IdolEntity?) {
    if (data != null) {
        val url = data.iconUrl
        val placeHolder = when (data.idolType) {
            IdolField.IdolType.PRINCESS -> R.color.princess
            IdolField.IdolType.FAIRY -> R.color.fairy
            IdolField.IdolType.ANGEL -> R.color.angle
            IdolField.IdolType.EX -> android.R.color.transparent
            else -> android.R.color.transparent
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

@BindingAdapter("imgUrl")
fun loadImage(imageView: ImageView, url: String?) {
    if (url != null) {
        Glide.with(imageView.context)
                .load(url)
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
            IdolField.Rarity.N -> null
            IdolField.Rarity.R -> R.drawable.ic_r_border
            IdolField.Rarity.SR -> R.drawable.ic_sr_border
            IdolField.Rarity.SSR -> R.drawable.ic_ssr_border
            else -> null
        }
        if (borderRes != null) {
            Glide.with(imageView.context)
                    .load(borderRes)
                    .into(imageView)
        } else {
            Glide.with(imageView.context)
                    .clear(imageView)
        }
    } else {
        Glide.with(imageView.context)
                .clear(imageView)
    }
}

@BindingAdapter("idolType")
fun setIconIdolType(imageView: ImageView, idolType: Int?) {
    val iconRes = when (idolType) {
        IdolField.IdolType.PRINCESS -> R.drawable.ic_princess
        IdolField.IdolType.FAIRY -> R.drawable.ic_fairy
        IdolField.IdolType.ANGEL -> R.drawable.ic_angle
        IdolField.IdolType.EX -> R.drawable.ic_ex
        else -> null
    }
    if (iconRes != null) {
        Glide.with(imageView.context)
                .load(iconRes)
                .into(imageView)
    } else {
        Glide.with(imageView.context)
                .clear(imageView)
    }
}

@BindingAdapter("extraType")
fun setIconExtraType(imageView: ImageView, extraType: Int?) {
    val iconRes = when (extraType) {
        IdolField.ExtraType.ANVI_1 -> R.drawable.ic_1stanv
        IdolField.ExtraType.ANVI_2 -> R.drawable.ic_2ndanv
        IdolField.ExtraType.ANVI_3 -> R.drawable.ic_3rdanv
        IdolField.ExtraType.PST_RANK,
        IdolField.ExtraType.PST_POINT -> R.drawable.ic_pst
        IdolField.ExtraType.FES -> R.drawable.ic_fes
        else -> null
    }
    if (iconRes != null) {
        Glide.with(imageView.context)
                .load(iconRes)
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

@BindingAdapter("cardBG")
fun loadImg(imageView: ImageView, url: String?) {
    if (url != null) {
        val progress = CircularProgressDrawable(imageView.context).apply {
            strokeWidth = 5f.convertDp2Px()
            centerRadius = 30f.convertDp2Px()
            setColorSchemeColors(ContextCompat.getColor(imageView.context, R.color.colorPrimary))
            start()
        }
        Glide.with(imageView.context)
                .load(url)
                .error(ColorDrawable(Color.TRANSPARENT))
                .transition(withCrossFade())
                .placeholder(progress)
                .into(imageView)
    } else {
        Glide.with(imageView.context)
                .clear(imageView)
    }
}