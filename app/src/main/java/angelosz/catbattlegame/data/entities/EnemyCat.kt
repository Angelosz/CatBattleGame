package angelosz.catbattlegame.data.entities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
    @StringRes val name: Int = R.string.enemy_wool_ball,
    @StringRes val description: Int = R.string.enemy_wool_ball_desc,
    @DrawableRes val image: Int = R.drawable.enemy_wool_ball_300,
    val armorType: ArmorType = ArmorType.LIGHT,
    val role: CatRole = CatRole.DEFENDER,
    val baseHealth: Float = 50f,
    val baseAttack: Float = 10f,
    val baseDefense: Float = 10f,
    val attackSpeed: Float = 1f,
    val rarity: CatRarity = CatRarity.KITTEN,
    val enemyType: EnemyType = EnemyType.SIMPLE_ENEMY
)