package angelosz.catbattlegame.ui.armory.battlechest_view

import angelosz.catbattlegame.data.entities.BattleChest
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.enums.ArmoryBattleChestStage

data class ArmoryBattleChestUiState (
    val state: ScreenState = ScreenState.INITIALIZING,
    val battleChests: Map<Pair<CatRarity, BattleChestType>, List<BattleChest>> = mapOf(),
    val selectedBattleChest: BattleChest = BattleChest(),
    val armoryBattleChestView: ArmoryBattleChestStage = ArmoryBattleChestStage.GRID,
    val catReward: Cat = Cat(),
    val newCatIsDuplicated: Boolean = false,
)

