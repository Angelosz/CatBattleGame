package angelosz.catbattlegame.domain.models.entities

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole
import angelosz.catbattlegame.ui.campaign.EnemyType

@Entity(tableName = "campaign_enemies")
data class EnemyCat(
    @PrimaryKey
    val id: Long = 1,
    val name: String = "Enemy",
    val description: String = "Enemy description",
    @DrawableRes val image: Int = R.drawable.enemy_wool_ball_300,
    val armorType: ArmorType = ArmorType.LIGHT,
    val role: CatRole = CatRole.DEFENDER,
    val baseHealth: Int = 50,
    val baseAttack: Int = 10,
    val baseDefense: Int = 10,
    val attackSpeed: Float = 1f,
    val rarity: CatRarity = CatRarity.KITTEN,
    val enemyType: EnemyType = EnemyType.SIMPLE_ENEMY
)