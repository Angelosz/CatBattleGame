package angelosz.catbattlegame.ui.armory

import angelosz.catbattlegame.domain.enums.ScreenState

data class ArmoryUiState (
    val selectedCollection: ArmoryView = ArmoryView.CATS,
    val screenState: ScreenState = ScreenState.INITIALIZING,
    val isInADetailView: Boolean = false,

    /* Cats View */
    val cats: List<SimpleArmoryCatData> = listOf(),
    val selectedCat: DetailedArmoryCatData = DetailedArmoryCatData(),
    val isCatDetailView: Boolean = false,

    /* Teams View
    val teams: List<SimpleArmoryTeamData> = listOf(),
    //val selectedTeam:
    val isTeamDetailView: Boolean = false,*/

    /* Battle Chests View
    val battleChests: List<SimpleArmoryBattleChestData> = listOf(),
    //val selectedBattleChest:
    val isBattleChestDetailView: Boolean = false,*/
)