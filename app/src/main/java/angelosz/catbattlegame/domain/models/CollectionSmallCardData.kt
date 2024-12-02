package angelosz.catbattlegame.domain.models

import androidx.annotation.DrawableRes

data class CollectionSmallCardData(
    val id: Int,
    @DrawableRes val image: Int,
    val level: Int,
    val experience: Int
)