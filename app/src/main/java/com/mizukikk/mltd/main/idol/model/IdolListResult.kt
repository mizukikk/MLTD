package com.mizukikk.mltd.main.idol.model

sealed class IdolListResult {

    data class CheckDB(val dataEmpty: Boolean) : IdolListResult()
    data class Download(val success: Boolean, val totalItem: Int) : IdolListResult()
    data class SaveIdolData(val progress: Int, val maxProgress: Int) : IdolListResult() {
        val saveSuccess get() = progress == maxProgress
        val progressText get() = "$progress / $maxProgress"
    }

    companion object {
        fun dbDataEmpty(empty: Boolean) = CheckDB(empty)
        fun downloadResult(success: Boolean, totalItem: Int = 0) = Download(success, totalItem)
        fun setSaveDataProgress(progress: Int, maxProgress: Int) =
            SaveIdolData(progress, maxProgress)
    }
}