package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import angelosz.catbattlegame.R

enum class CatRarity(@StringRes val res: Int) {
    KITTEN(R.string.kitten),
    TEEN(R.string.teen),
    ADULT(R.string.adult),
    ELDER(R.string.elder);
}