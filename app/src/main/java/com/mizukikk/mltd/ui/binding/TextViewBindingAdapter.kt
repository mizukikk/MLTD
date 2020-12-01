package com.mizukikk.mltd.ui.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.R


@BindingAdapter("filterSelect")
fun setFilterSelect(textView: TextView, filterSelect: Boolean) {
    if (filterSelect) {
        textView.setTextColor(MLTDApplication.appContext.getColor(R.color.white))
        textView.background =
            MLTDApplication.appContext.getDrawable(R.drawable.bt_filter_select)
    } else {
        textView.setTextColor(MLTDApplication.appContext.getColor(R.color.gray))
        textView.background =
            MLTDApplication.appContext.getDrawable(R.drawable.bt_filter_unselect)
    }
}