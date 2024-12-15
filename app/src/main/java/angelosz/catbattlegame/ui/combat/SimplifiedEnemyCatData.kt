package angelosz.catbattlegame.ui.combat

import androidx.annotation.DrawableRes

data class SimplifiedEnemyCatData(
    val id: Long,
    val name: String,
    val description: String,
    @DrawableRes val image: Int
)
