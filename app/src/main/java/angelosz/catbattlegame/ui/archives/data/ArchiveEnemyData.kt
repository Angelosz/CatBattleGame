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
    val baseHealth: Int = 50,
    val baseAttack: Int = 10,
    val baseDefense: Int = 10,
    val attackSpeed: Float = 1f,
    val enemyType: EnemyType = EnemyType.SIMPLE_ENEMY,
    val abilities: List<Ability> = emptyList(),
    val isDiscovered: Boolean = false
)

