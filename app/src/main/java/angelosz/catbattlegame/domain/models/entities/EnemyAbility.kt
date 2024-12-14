package angelosz.catbattlegame.domain.models.entities

import androidx.room.Entity

@Entity(primaryKeys = ["enemyCatId", "abilityId"], tableName = "campaign_enemy_ability")
data class EnemyAbility(
    val enemyCatId: Long = 1,
    val abilityId: Int = 1
)