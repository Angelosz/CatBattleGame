package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import angelosz.catbattlegame.R

enum class DamageType(@StringRes val res: Int, val color: Color) {
    BLUNT(R.string.ability_type_blunt, Color.Red),
    PIERCING(R.string.ability_type_piercing, Color.Red),
    HEROIC(R.string.ability_type_heroic, Color.Yellow),
    ELEMENTAL(R.string.ability_type_elemental, Color.Magenta),
    ETHEREAL(R.string.ability_type_ethereal, Color.Blue),
    POISON(R.string.poison, Color.Green)
}