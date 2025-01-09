package angelosz.catbattlegame.data.entities.notifications

import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.domain.enums.RewardType
import angelosz.catbattlegame.ui.home.notifications.NotificationType

@Entity(tableName = "currency_notifications")
data class CurrencyNotificationEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: RewardType = RewardType.GOLD,
    val amount: Int = 20,
    val notificationText: String = "Congratulations! You won $amount gold coins!",
    val notificationType: NotificationType = NotificationType.CURRENCY_REWARD
)