package angelosz.catbattlegame.navigation

import angelosz.catbattlegame.domain.enums.CombatResult
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

@Serializable
object EncyclopediaScreenRoute

@Serializable
object CollectionScreenRoute

@Serializable
object BattleChestsScreenRoute

@Serializable
object TeamBuilderScreenRoute

@Serializable
object CampaignMenuScreenRoute

@Serializable
data class TeamSelectionScreenRoute(val chapterId: Long)

@Serializable
data class CombatScreenRoute(val teamId: Long, val chapterId: Long)

@Serializable
data class CombatResultScreenRoute(val teamId: Long, val chapterId: Long, val combatResult: CombatResult)