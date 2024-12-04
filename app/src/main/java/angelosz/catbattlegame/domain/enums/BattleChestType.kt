package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import angelosz.catbattlegame.R

enum class BattleChestType(@StringRes val res: Int) {
    NORMAL(R.string.battlechest_normal_display),
    NEW_CAT(R.string.battlechest_new_cat_display)
}