package com.mizukikk.mltd.main

import android.view.View
import com.mizukikk.mltd.room.query.IdolItem

interface InteractiveMainActivity {
    fun setIdolFragment(view: View, idolItem: IdolItem)
}