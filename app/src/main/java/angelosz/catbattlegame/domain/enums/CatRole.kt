package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import angelosz.catbattlegame.R

enum class CatRole(@StringRes val res: Int) {
    WARRIOR(R.string.catrole_warrior),
    ASSASSIN(R.string.catrole_assassin),
    MAGE(R.string.catrole_mage),
    HEALER(R.string.catrole_healer),
    DEFENDER(R.string.catrole_defender);
}