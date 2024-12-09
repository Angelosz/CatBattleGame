package angelosz.catbattlegame.domain.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_owned_cat")
data class OwnedCat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val catId: Int = 1,
    val healthModifier: Int = 0,
    val attackModifier: Int = 0,
    val defenseModifier: Int = 0,
    val attackSpeedModifier: Float = 0.0f,
    val level: Int = 1,
    val experience: Int = 0
)
