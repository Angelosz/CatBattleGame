package angelosz.catbattlegame.data.entities.notifications

import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.ui.home.notifications.NotificationType

@Entity(tableName = "notifications")
data class NotificationsEntity (
    @PrimaryKey
    val notificationId: Long,
    val notificationType: NotificationType,
)
