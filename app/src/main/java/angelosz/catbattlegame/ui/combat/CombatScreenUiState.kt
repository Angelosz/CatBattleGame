package angelosz.catbattlegame.ui.combat

import androidx.annotation.DrawableRes
import angelosz.catbattlegame.data.entities.CampaignChapter
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.CombatStage
import angelosz.catbattlegame.domain.enums.CombatState
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.teambuilder.TeamData

data class CombatScreenUiState(
    val chapter: CampaignChapter = CampaignChapter(),
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

    val numberOfTurns: Int = 0
)

data class CatInitiativeData(
    val catId: Int,
    @DrawableRes val image: Int,
    var initiative: Float
)