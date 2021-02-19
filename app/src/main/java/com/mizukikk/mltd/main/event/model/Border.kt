package com.mizukikk.mltd.main.event.model

import java.text.NumberFormat

data class Border(
        val rank: Int,
        private val score: Int,
        private val halfHourAdd: Int = 0,
        private val oneHourAdd: Int = 0,
        private val oneDayAdd: Int = 0
) {
    val currentScore get() = NumberFormat.getNumberInstance().format(score)!!
    val halfHour get() = NumberFormat.getNumberInstance().format(halfHourAdd)!!
    val oneHour get() = NumberFormat.getNumberInstance().format(oneHourAdd)!!
    val oneDay get() = NumberFormat.getNumberInstance().format(oneDayAdd)!!
}