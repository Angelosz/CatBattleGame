package angelosz.catbattlegame.data.entities.notifications

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.home.notifications.NotificationType

@Entity(tableName = "cat_notifications")
data class CatNotificationEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @StringRes val catName: Int = R.string.the_swordsman_name,
    @DrawableRes val catImage: Int = R.drawable.kitten_swordman_300x300,
    val notificationText: String = "Congratulations! Your cat leveled up!",
    val level: Int = 4,
    @StringRes val evolutionName: Int = R.string.the_swordsman_name,
    @DrawableRes val evolutionImage: Int = R.drawable.teen_swordman_300x300,
    val notificationType: NotificationType = NotificationType.LEVEL_UP
)

