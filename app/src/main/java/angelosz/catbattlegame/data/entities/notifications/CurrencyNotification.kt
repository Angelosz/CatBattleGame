package angelosz.catbattlegame.data.entities.notifications

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.home.notifications.NotificationType

@Entity(tableName = "currency_notifications")
data class CurrencyNotificationEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @DrawableRes val currencyImage: Int = R.drawable.goldcoins_128,
    val amount: Int = 20,
    val notificationText: String = "Congratulations! You won $amount gold coins!",
    val notificationType: NotificationType = NotificationType.CURRENCY_REWARD
)