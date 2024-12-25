package angelosz.catbattlegame.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity

@Entity(tableName = "battle_chests")
data class BattleChest(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rarity: CatRarity = CatRarity.KITTEN,
    val type: BattleChestType = BattleChestType.NORMAL
)

