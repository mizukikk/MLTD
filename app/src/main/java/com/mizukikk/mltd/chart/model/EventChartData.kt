package com.mizukikk.mltd.chart.model

import android.os.Parcelable
import com.mizukikk.mltd.data.model.EventField
import kotlinx.android.parcel.Parcelize


@Parcelize
data class EventChartData(
        val eventId: Int,
        val type: String?,
        val ranks: String?
) : Parcelable {
    val isAnivEvent get() = EventField.Borders.IDOL_POINT == type
}