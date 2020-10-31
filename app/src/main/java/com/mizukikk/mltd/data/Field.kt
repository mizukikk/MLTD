package com.mizukikk.mltd.data

object Field {
    object Skill {
        // スコアアップ
        const val SCORE_UP = 1

        // コンボボーナス
        const val COMBO_BONUS = 2

        // ライフ回復
        const val LIFE_RECOVERY = 3

        // ダメージガード
        const val DAMAGE_GUARD = 4

        // コンボ継続
        const val CONTINUE_COMBO = 5

        // 判定強化
        const val STRENGTHEN_JUDGMENT = 6

        // ダブルブースト
        const val DOUBLE_BOOST = 7

        // マルチアップ
        const val MULTI_UP = 8

        // オーバークロック
        const val OVERCLOCK = 10

        // オーバーロンド
        const val OVERLOAD = 11
    }

    object IdolType {
        //Princess
        const val PRINCESS = 1

        //Fairy
        const val FAIRY = 2

        //Angel
        const val ANGEL = 3

        //Ex
        const val EX = 5
    }

    object Rarity {
        //N
        const val N = 1

        //R
        const val R = 2

        //SR
        const val SR = 3

        //SSR
        const val SSR = 4
    }
}