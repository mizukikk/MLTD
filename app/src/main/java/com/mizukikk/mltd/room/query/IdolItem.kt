package com.mizukikk.mltd.room.query

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.room.Embedded
import androidx.room.Relation
import com.mizukikk.mltd.MLTDApplication
import com.mizukikk.mltd.R
import com.mizukikk.mltd.data.Field
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
                Field.Skill.SCORE_UP -> getString(R.string.skill_score_up)
                Field.Skill.COMBO_BONUS -> getString(R.string.skill_combo_bonus)
                Field.Skill.LIFE_RECOVERY -> getString(R.string.skill_life_recovery)
                Field.Skill.DAMAGE_GUARD -> getString(R.string.skill_damage_guard)
                Field.Skill.CONTINUE_COMBO -> getString(R.string.skill_continue_combo)
                Field.Skill.STRENGTHEN_JUDGMENT -> getString(R.string.skill_strengthen_judgment)
                Field.Skill.DOUBLE_BOOST -> getString(R.string.skill_double_boost)
                Field.Skill.MULTI_UP -> getString(R.string.skill_multi_up)
                Field.Skill.OVERCLOCK -> getString(R.string.skill_overclock)
                Field.Skill.OVERLOAD -> getString(R.string.skill_overload)
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
    val vo get() = idol.vocalMaxAwakened.toString()
    val vi get() = idol.visualMaxAwakened.toString()
    val da get() = idol.danceMaxAwakened.toString()
    val iconUrl get() = "https://storage.matsurihi.me/mltd/icon_l/${idol.resourceId}_1.png"

    private fun getString(@StringRes id: Int) =
        MLTDApplication.applicationContext.getString(id)
}