package angelosz.catbattlegame.domain.models

import androidx.annotation.DrawableRes

data class BasicCatData(
    val catId: Int,
    @DrawableRes val image: Int
)