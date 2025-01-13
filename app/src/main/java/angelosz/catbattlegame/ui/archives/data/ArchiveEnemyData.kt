package angelosz.catbattlegame.ui.archives.data

import androidx.annotation.DrawableRes
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.ui.campaign.EnemyType

data class ArchiveEnemyData(
    val id: Long = 1,
    val name: String = "Enemy",
    val description: String = "Enemy description",
    @DrawableRes val image: Int = R.drawable.enemy_wool_ball_300,
    val armorType: ArmorType = ArmorType.LIGHT,
    val baseHealth: Float = 50f,
    val baseAttack: Float = 10f,
    val baseDefense: Float = 10f,
    val attackSpeed: Float = 1f,
    val enemyType: EnemyType = EnemyType.SIMPLE_ENEMY,
    val abilities: List<Ability> = emptyList(),
    val isDiscovered: Boolean = false
)

