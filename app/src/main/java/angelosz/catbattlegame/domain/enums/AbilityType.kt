package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import angelosz.catbattlegame.R

enum class AbilityType(@StringRes val res: Int) {
    HEALING(R.string.ability_type_healing),
    DAMAGE(R.string.ability_type_damage),
    STATUS_CHANGING(R.string.ability_type_status_changing),
    DAMAGE_STATUS_CHANGING(R.string.damage_status_changing),
    SUMMON(R.string.ability_type_summon),
    CLONE(R.string.ability_type_clone)
}

