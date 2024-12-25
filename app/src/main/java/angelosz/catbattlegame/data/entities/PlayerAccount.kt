package angelosz.catbattlegame.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_account")
data class PlayerAccount (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val accountLevel: Int = 1,
    val accountExperience: Int = 0,
    val gold: Int = 1000,
    val crystals: Int = 0,
    val lastLogin: Long = System.currentTimeMillis(),
    val lastDailyReward: Long = System.currentTimeMillis()
)