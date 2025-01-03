package angelosz.catbattlegame.navigation

import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.ui.armory.enums.CollectionsView
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

@Serializable
data class ArmoryScreenRoute(val selectedCollection: CollectionsView)

@Serializable
data class ArchiveScreenRoute(val selectedArchive: CollectionsView)

@Serializable
object CampaignMenuScreenRoute

@Serializable
data class TeamSelectionScreenRoute(val chapterId: Long)

@Serializable
data class CombatScreenRoute(val teamId: Long, val chapterId: Long)

@Serializable
data class CombatResultScreenRoute(val teamId: Long, val chapterId: Long, val combatResult: CombatResult)