package com.mizukikk.mltd.room.query

import androidx.room.Embedded
import androidx.room.Relation
import com.mizukikk.mltd.room.entity.*

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
)