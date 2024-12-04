package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import angelosz.catbattlegame.R

enum class ArmorType(@StringRes val res: Int) {
    LIGHT(R.string.armortype_light),
    MEDIUM(R.string.armortype_medium),
    HEAVY(R.string.armortype_heavy),
    HEROIC(R.string.armortype_heroic),
    ELEMENTAL(R.string.armortype_elemental),
    ETHEREAL(R.string.armortype_ethereal);
}