package com.mizukikk.mltd.api.response

import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.R
import com.mizukikk.mltd.api.obj.LastPointData
import com.mizukikk.mltd.data.model.EventField


class GetLastPointResponse : HashMap<String, LastPointData>() {


    fun getLastPointList(): List<LastPointData> {
        val eventList = mutableListOf<LastPointData>()
        this[EventField.Borders.EVENT_POINT]?.let {
            it.title = MLTDApplication.appContext.getString(R.string.item_last_point_pt_border)
            eventList.add(it)
        }
        this[EventField.Borders.HIGH_SCORE]?.let {
            it.title = MLTDApplication.appContext.getString(R.string.item_last_point_score_border)
            eventList.add(it)
        }
        this[EventField.Borders.HIGH_SCORE2]?.let {
            it.title = MLTDApplication.appContext.getString(R.string.item_last_point_score2_border)
            eventList.add(it)
        }
        this[EventField.Borders.HIGH_SCORE_TOTAL]?.let {
            it.title =
                MLTDApplication.appContext.getString(R.string.item_last_point_score_total_border)
            eventList.add(it)
        }
        this[EventField.Borders.LOUNGE_POINT]?.let {
            it.title = MLTDApplication.appContext.getString(R.string.item_last_point_loung_board)
            eventList.add(it)
        }
        return eventList
    }

}




