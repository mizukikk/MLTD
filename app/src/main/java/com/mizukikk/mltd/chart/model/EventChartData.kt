package com.mizukikk.mltd.chart.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class EventChartData(
        val eventId: Int,
        val type: String?,
        val ranks: String?
) : Parcelable