package angelosz.catbattlegame.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enemy_discovery")
data class EnemyDiscoveryState (
    @PrimaryKey
    val enemyCatId: Long,
    val state: Boolean
)