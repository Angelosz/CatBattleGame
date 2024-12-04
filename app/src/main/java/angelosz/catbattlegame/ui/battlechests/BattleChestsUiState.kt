package angelosz.catbattlegame.ui.battlechests

import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.models.entities.BattleChest
import angelosz.catbattlegame.domain.models.entities.Cat

data class BattleChestsUiState(
    val battleChests: Map<Pair<CatRarity, BattleChestType>, List<BattleChest>> = mapOf(),
    val catReward: Cat? = null,
    val selectedBattleChest: BattleChest? = null
)
