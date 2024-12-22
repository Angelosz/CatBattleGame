package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import angelosz.catbattlegame.R

enum class DamageType(@StringRes val res: Int) {
    BLUNT(R.string.abilitytype_blunt),
    PIERCING(R.string.abilitytype_piercing),
    HEROIC(R.string.abilitytype_heroic),
    ELEMENTAL(R.string.abilitytype_elemental),
    ETHEREAL(R.string.abilitytype_ethereal),
}