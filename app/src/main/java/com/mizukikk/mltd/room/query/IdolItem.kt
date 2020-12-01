package com.mizukikk.mltd.room.query

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.room.Embedded
import androidx.room.Relation
import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.R
import com.mizukikk.mltd.data.model.IdolField
import com.mizukikk.mltd.main.idol.model.IdolStatus
import com.mizukikk.mltd.room.entity.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IdolItem(
    @Embedded
    val idol: IdolEntity,
    @Relation(parentColumn = "centerEffectId", entityColumn = "centerEffectId")
    val centerEffectEntity: CenterEffectEntity?,
    @Relation(parentColumn = "skillId", entityColumn = "skillId")
    val skill: SkillEntity?,
    @Relation(parentColumn = "costumeId", entityColumn = "costumeId")
    val costumeEntity: CostumeEntity?,
    @Relation(parentColumn = "bonusCostumeId", entityColumn = "bonusCostumeId")
    val bonusCostumeEntity: BonusCostumeEntity?,
    @Relation(parentColumn = "rank5CostumeId", entityColumn = "rank5CostumeId")
    val rank5CostumeEntity: Rank5CostumeEntity?
) : Parcelable {
    val idolTitle
        get() = run {
            val nameSplitList = idol.name.split("　")
            if (nameSplitList.size == 2) {
                nameSplitList[0]
            } else {
                ""
            }
        }
    val idolName
        get() = run {
            val nameSplitList = idol.name.split("　")
            if (nameSplitList.size == 2) {
                nameSplitList[1]
            } else {
                idol.name
            }
        }

    val idolSkill
        get() = run {
            val skillEffect: String = when (skill?.effectId) {
                IdolField.Skill.SCORE_UP -> getString(R.string.skill_score_up)
                IdolField.Skill.COMBO_BONUS -> getString(R.string.skill_combo_bonus)
                IdolField.Skill.LIFE_RECOVERY -> getString(R.string.skill_life_recovery)
                IdolField.Skill.DAMAGE_GUARD -> getString(R.string.skill_damage_guard)
                IdolField.Skill.CONTINUE_COMBO -> getString(R.string.skill_continue_combo)
                IdolField.Skill.STRENGTHEN_JUDGMENT -> getString(R.string.skill_strengthen_judgment)
                IdolField.Skill.DOUBLE_BOOST -> getString(R.string.skill_double_boost)
                IdolField.Skill.MULTI_UP -> getString(R.string.skill_multi_up)
                IdolField.Skill.OVERCLOCK -> getString(R.string.skill_overclock)
                IdolField.Skill.OVERLOAD -> getString(R.string.skill_overload)
                else -> getString(R.string.skill_empty)
            }
            if (skill != null) {
                val skillFormat = "%ds %s"
                skillFormat.format(skill.duration, skillEffect)
            } else {
                skillEffect
            }
        }

    val centerEffect get() = idol.centerEffectName

    val life get() = idol.life.toString()

    val voMax get() = "${idol.vocalMax}"
    val viMax get() = "${idol.visualMax}"
    val daMax get() = "${idol.danceMax}"

    val voMRMax get() = "${idol.vocalMax + idol.vocalMasterBonus * idol.masterRankMax}"
    val viMRMax get() = "${idol.visualMax + idol.visualMasterBonus * idol.masterRankMax}"
    val daMRMax get() = "${idol.danceMax + idol.danceMasterBonus * idol.masterRankMax}"

    val voAwakened get() = "${idol.vocalMaxAwakened}"
    val viAwakened get() = "${idol.visualMaxAwakened}"
    val daAwakened get() = "${idol.danceMaxAwakened}"

    val voAwakenedMRMax get() = "${idol.vocalMaxAwakened + idol.vocalMasterBonus * idol.masterRankMax}"
    val viAwakenedMRMax get() = "${idol.visualMaxAwakened + idol.visualMasterBonus * idol.masterRankMax}"
    val daAwakenedMRMax get() = "${idol.danceMaxAwakened + idol.danceMasterBonus * idol.masterRankMax}"

    val voText get() = "$voMax ($voMRMax)"
    val viText get() = "$viMax ($viMRMax)"
    val daText get() = "$daMax ($daMRMax)"

    val voAwakenedText get() = "$voAwakened ($voAwakenedMRMax)"
    val viAwakenedText get() = "$viAwakened ($viAwakenedMRMax)"
    val daAwakenedText get() = "$daAwakened ($daAwakenedMRMax)"

    val totalText
        get():String {
            val total = idol.vocalMax + idol.visualMax + idol.danceMax
            val voMaxMR = idol.vocalMax + idol.vocalMasterBonus * idol.masterRankMax
            val viMaxMR = idol.visualMax + idol.visualMasterBonus * idol.masterRankMax
            val daMaxMR = idol.danceMax + idol.danceMasterBonus * idol.masterRankMax
            val totalMaxMR = voMaxMR + viMaxMR + daMaxMR
            return "$total ($totalMaxMR)"
        }
    val totalAwakenedText
        get():String {
            val total = idol.vocalMaxAwakened + idol.visualMaxAwakened + idol.danceMaxAwakened
            val voMaxMR = idol.vocalMaxAwakened + idol.vocalMasterBonus * idol.masterRankMax
            val viMaxMR = idol.visualMaxAwakened + idol.visualMasterBonus * idol.masterRankMax
            val daMaxMR = idol.danceMaxAwakened + idol.danceMasterBonus * idol.masterRankMax
            val totalMaxMR = voMaxMR + viMaxMR + daMaxMR
            return "$total ($totalMaxMR)"
        }

    val skillText
        get():String? {
            return if (skill != null) {
                val maximumPercent = if (idol.masterRankMax > 4) {
                    skill.probability + 20
                } else {
                    skill.probability + 10
                }
                val minimumPercent = skill.probability + 1
                skill.description.replace("{0}", "$minimumPercent-$maximumPercent")
            } else {
                null
            }
        }

    fun getStatusData(awakened: Boolean) = if (awakened) {
        IdolStatus(
            voAwakenedText,
            viAwakenedText,
            daAwakenedText,
            totalAwakenedText,
            idol.levelMaxAwakened.toString()
        )
    } else {
        IdolStatus(voText, viText, daText, totalText, idol.levelMax.toString())

    }

    private fun getString(@StringRes id: Int) =
        MLTDApplication.appContext.getString(id)
}