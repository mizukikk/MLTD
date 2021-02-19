package com.mizukikk.mltd.main.event.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mizukikk.mltd.extension.convertDp2Px

class AnivIdolDecoration(private val count: Int) : RecyclerView.ItemDecoration() {

    private val margin by lazy { 4f.convertDp2Px().toInt() }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val pos = parent.getChildAdapterPosition(view)
        val lastIndex = count - 1
        val marginTop = if (pos in 0 until count) margin else 0
        when (pos % count) {
            0 ->
                outRect.set(margin, marginTop, margin / 2, margin)
            lastIndex ->
                outRect.set(margin / 2, marginTop, margin, margin)
            else ->
                outRect.set(margin / 2, marginTop, margin / 2, margin)
        }

    }
}