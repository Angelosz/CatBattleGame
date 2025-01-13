package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import angelosz.catbattlegame.R

enum class AbilityType(@StringRes val res: Int) {
    HEALING(R.string.abilitytype_healing),
    DAMAGE(R.string.abilitytype_damage),
    STATUS_CHANGING(R.string.abilitytype_status_changing),
    DAMAGE_STATUS_CHANGING(R.string.damage_status_changing);
}

