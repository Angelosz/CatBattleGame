package angelosz.catbattlegame.ui.archives.data

import androidx.annotation.DrawableRes
import angelosz.catbattlegame.R

data class SimpleArchiveEnemyData(
    val id: Long = 1,
    @DrawableRes val image: Int = R.drawable.enemy_wool_ball_300,
    val isDiscovered: Boolean = false
)