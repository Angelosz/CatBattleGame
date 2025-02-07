package angelosz.catbattlegame.ui.combat

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.datastore.PlayerSettings
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.CombatStage
import angelosz.catbattlegame.domain.enums.CombatState
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.campaign.data.Chapter

data class CombatScreenUiState(
    val chapter: Chapter = Chapter(),
    val team: TeamData = TeamData(0, "", listOf()),
    val chapterEnemies: List<EnemyCat> = listOf(),
    val screenState: ScreenState = ScreenState.INITIALIZING,
    val combatState: CombatState = CombatState.NOT_LOADED,
    val combatStage: CombatStage = CombatStage.PLAYER_TURN,
    val combatResult: CombatResult = CombatResult.TIED,

    val teamCombatCats: List<CombatCat> = emptyList(),
    val enemyCombatCats: List<EnemyCombatCat> = emptyList(),
    val catInitiatives: List<CatInitiativeData> = emptyList(),

    val activeCatId: Int = 0,
    val activeAbility: CombatAbility? = null,
    @StringRes val abilityMessage: Int = R.string.combat_starting,
    val numberOfTurns: Int = 0,

    val settingsIsOpen: Boolean = false,
    val playerSettings: PlayerSettings = PlayerSettings(),
)

data class CatInitiativeData(
    val catId: Int,
    @DrawableRes val image: Int,
    var initiative: Float
)