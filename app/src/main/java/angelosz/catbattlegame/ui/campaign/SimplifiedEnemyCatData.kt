package angelosz.catbattlegame.ui.campaign

import androidx.annotation.DrawableRes

data class SimplifiedEnemyCatData(
    val id: Long,
    val name: String,
    val description: String,
    @DrawableRes val image: Int
)
