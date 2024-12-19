package angelosz.catbattlegame.navigation

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
data class CombatResolutionScreenRoute(val teamId: Long, val chapterId: Long, val success: Boolean)