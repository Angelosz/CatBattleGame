package angelosz.catbattlegame.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.domain.enums.RewardType

@Entity(tableName = "player_mail")
data class MailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val isRewardClaimed: Boolean = false,
    val rewardType: RewardType? = null,
    val rewardAmount: Int = 0
)
