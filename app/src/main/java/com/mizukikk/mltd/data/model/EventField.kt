package com.mizukikk.mltd.data.model

object EventField {

    const val EVENT_IMG_URL_FORMAT = "https://storage.matsurihi.me/mltd/event_bg/%s.png"

    object Borders {
        const val EVENT_POINT = "eventPoint"
        const val HIGH_SCORE = "highScore"
        const val HIGH_SCORE2 = "highScore2"
        const val HIGH_SCORE_TOTAL = "highScoreTotal"
        const val LOUNGE_POINT = "loungePoint"
        const val IDOL_POINT = "idolPoint"
    }

    object Type{
        const val THEATER_SHOW_TIME = 1//THEATER SHOW TIME☆
        const val COLLECTION = 2//ミリコレ！
        const val THEATER = 3//プラチナスターシアター
        const val TOUR = 4//プラチナスターツアー
        const val ANNIVERSARY = 5//周年記念イベント
        const val WORKING = 6//MILLION LIVE WORKING☆
        const val APRIL_FOOL = 7//エイプリルフール
        const val COLLECTION_BOX = 9//ミリコレ！（ボックスガシャ）
        const val TWIN_STAGE = 10//ツインステージ
        const val TUNE = 11//プラチナスターチューン
        const val TWIN_STAGE2 = 12//ツインステージ 2
        const val TAIL = 13//プラチナスターテール
    }

}