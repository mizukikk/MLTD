package com.mizukikk.mltd.main

import android.net.Uri
import android.view.View
import com.mizukikk.mltd.room.query.IdolItem

interface InteractiveMainActivity {
    fun setIdolFragment(shareView: View, idolItem: IdolItem)
    fun showPhoto(
        shareView: View,
        photoUri: Uri,
        data: IdolItem
    )
}