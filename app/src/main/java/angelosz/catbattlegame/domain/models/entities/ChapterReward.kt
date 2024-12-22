package angelosz.catbattlegame.domain.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.domain.enums.RewardType

@Entity(tableName = "chapter_reward")
data class ChapterReward (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterId: Long,
    val rewardType: RewardType,
    val amount: Int = 1
)

