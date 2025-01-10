package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import angelosz.catbattlegame.R

enum class DamageType(@StringRes val res: Int, val color: Color) {
    BLUNT(R.string.abilitytype_blunt, Color.Red),
    PIERCING(R.string.abilitytype_piercing, Color.Red),
    HEROIC(R.string.abilitytype_heroic, Color.Yellow),
    ELEMENTAL(R.string.abilitytype_elemental, Color.Magenta),
    ETHEREAL(R.string.abilitytype_ethereal, Color.Blue),
    POISON(R.string.poison, Color.Green)
}