package com.mizukikk.mltd.main.event.model

import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.R
import com.mizukikk.mltd.api.obj.LastPointData
import com.mizukikk.mltd.data.model.EventField
import com.mizukikk.mltd.room.query.IdolItem

data class EventBorder(
        val title: String,
        val updateDate: String,
        val borderList: List<Border>,
        val idolData: IdolItem? = null
) {
    constructor(data: LastPointData) : this(data.title!!, data.updateDate, data.borderList)

    val eventChartType
        get() = when {
            title == MLTDApplication.appContext.getString(R.string.item_last_point_pt_border) -> EventField.Borders.EVENT_POINT
            title == MLTDApplication.appContext.getString(R.string.item_last_point_score_border) -> EventField.Borders.HIGH_SCORE
            title == MLTDApplication.appContext.getString(R.string.item_last_point_score2_border) -> EventField.Borders.HIGH_SCORE2
            title == MLTDApplication.appContext.getString(R.string.item_last_point_score_total_border) -> EventField.Borders.HIGH_SCORE_TOTAL
            title == MLTDApplication.appContext.getString(R.string.item_last_point_loung_board) -> EventField.Borders.LOUNGE_POINT
            idolData != null -> EventField.Borders.IDOL_POINT
            else -> ""
        }
    val eventChartRanks get() = borderList.filter { it.rank <= 50000 }.map { it.rank }.joinToString(",")
}