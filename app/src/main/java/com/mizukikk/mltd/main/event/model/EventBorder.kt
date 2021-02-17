package com.mizukikk.mltd.main.event.model

import com.mizukikk.mltd.api.obj.LastPointData
import com.mizukikk.mltd.room.query.IdolItem

data class EventBorder(
    val title: String,
    val updateDate: String,
    val borderList: List<Border>,
    val idolData: IdolItem? = null
) {
    constructor(data: LastPointData) : this(data.title!!, data.updateDate, data.borderList)
}