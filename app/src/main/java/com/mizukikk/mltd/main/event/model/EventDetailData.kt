package com.mizukikk.mltd.main.event.model

import android.os.Parcelable
import com.mizukikk.mltd.api.response.EventResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventDetailData(val eventData: EventResponse) : Parcelable