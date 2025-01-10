package angelosz.catbattlegame.data.entities.notifications

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.home.notifications.NotificationType

@Entity(tableName = "cat_notifications")
data class CatNotificationEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val catName: String = "Bob, the Kitten Swordsman",
    @DrawableRes val catImage: Int = R.drawable.kitten_swordman_300x300,
    val notificationText: String = "Congratulations! Your cat leveled up!",
    val level: Int = 4,
    val evolutionName: String = "Bob, the teen Swordsman",
    @DrawableRes val evolutionImage: Int = R.drawable.teen_swordman_300x300,
    val notificationType: NotificationType = NotificationType.LEVEL_UP
)

