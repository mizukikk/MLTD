package com.mizukikk.mltd.data.model

object IdolField {
    object URL {
        const val CARD_BG_FORMAT = "https://storage.matsurihi.me/mltd/card_bg/%s.png"
        const val CARD_FORMAT = "https://storage.matsurihi.me/mltd/card/%s.png"
        const val ICON_FORMAT = "https://storage.matsurihi.me/mltd/icon_l/%s.png"
    }

    object IdolType {
        const val PRINCESS = 1
        const val FAIRY = 2
        const val ANGEL = 3
        const val EX = 4
    }

    object Rarity {
        const val N = 1
        const val R = 2
        const val SR = 3
        const val SSR = 4
    }

    object Category {
        //初期ノーマル
        const val NORMAL = "normal"

        //恒常ガシャ
        const val GASHA0 = "gasha0"

        //限定ガシャ
        const val GASHA1 = "gasha1"

        //フェス限定
        const val GASHA2 = "gasha2"

        //ミリコレ（SR）
        const val EVENT0 = "event0"

        //シアター
        const val EVENT1 = "event1"

        //ツアー
        const val EVENT2 = "event2"

        //周年イベント
        const val EVENT3 = "event3"

        //投票イベント追加 SR（超ビーチバレー ロコなど）
        const val EVENT4 = "event4"

        //ミリコレ（R）
        const val EVENT5 = "event5"

        //その他
        const val OTHER = "other"

    }

    object ExtraType {
        // なし
        const val NONE = 0

        // PST ランキング報酬
        const val PST_RANK = 2

        // PST ポイント報酬
        const val PST_POINT = 3

        // フェス
        const val FES = 4

        // 1 周年イベント報酬
        const val ANVI_1 = 5

        // Extra カード
        const val EXTRA = 6

        // 2 周年イベント報酬
        const val ANVI_2 = 7

        // Extra PST ランキング報酬
        const val EXTRA_RANK = 8

        // Extra PST ポイント報酬
        const val EXTRA_POINT = 9

        // 3 周年イベント報酬
        const val ANVI_3 = 10

    }

    object Skill {
        //スコアアップ
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


}