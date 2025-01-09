package angelosz.catbattlegame.data.entities.notifications

import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.ui.home.notifications.NotificationType

@Entity(tableName = "battle_chest_notifications")
data class BattleChestNotificationEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val battleChestRarity: CatRarity = CatRarity.KITTEN,
    val battleChestType: BattleChestType = BattleChestType.NORMAL,
    val notificationType: NotificationType = NotificationType.BATTLE_CHEST_REWARD,
    val notificationText: String = ""
)