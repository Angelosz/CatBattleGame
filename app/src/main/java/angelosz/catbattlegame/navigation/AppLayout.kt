package angelosz.catbattlegame.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import angelosz.catbattlegame.ui.battlechests.BattleChestsScreen
import angelosz.catbattlegame.ui.campaign.CampaignScreen
import angelosz.catbattlegame.ui.combat.CombatScreen
import angelosz.catbattlegame.ui.combatreward.CombatResultScreen
import angelosz.catbattlegame.ui.encyclopedia.EncyclopediaScreen
import angelosz.catbattlegame.ui.home.HomeScreen
import angelosz.catbattlegame.ui.playercollection.CollectionScreen
import angelosz.catbattlegame.ui.teambuilder.TeamBuilderScreen
import angelosz.catbattlegame.ui.teamselection.TeamSelectionScreen

@Composable
fun AppLayout(
    windowSize: WindowWidthSizeClass
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute,
        modifier = Modifier
            .fillMaxSize()
    ) {
        composable<HomeScreenRoute> {
            HomeScreen(
                windowSize = windowSize,
                onPlayButtonClick = { navController.navigate(CampaignMenuScreenRoute) },
                navigateToEncyclopedia = { navController.navigate(EncyclopediaScreenRoute) },
                navigateToCollection = { navController.navigate(CollectionScreenRoute) },
                navigateToBattleChests = { navController.navigate(BattleChestsScreenRoute) },
                navigateToTeamBuild = { navController.navigate(TeamBuilderScreenRoute) }
            )
        }
        composable<EncyclopediaScreenRoute> {
            EncyclopediaScreen(
                windowSize = windowSize,
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable<CollectionScreenRoute> {
            CollectionScreen(
                windowSize = windowSize,
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable<BattleChestsScreenRoute> {
            BattleChestsScreen(
                onBackPressed = { navController.navigateUp() },
                navigateToCollection = {
                    navController.popBackStack(HomeScreenRoute, false)
                    navController.navigate(CollectionScreenRoute)
                }
            )
        }
        composable<TeamBuilderScreenRoute> {
            TeamBuilderScreen(
                windowSize = windowSize,
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable<CampaignMenuScreenRoute>{
            CampaignScreen(
                windowSize = windowSize,
                onBackButtonPressed = { navController.navigateUp() },
                onChapterSelected = { chapterId -> navController.navigate(TeamSelectionScreenRoute(chapterId = chapterId))}
            )
        }
        composable<TeamSelectionScreenRoute>{ backStackEntry ->
            val data: TeamSelectionScreenRoute = backStackEntry.toRoute()
            TeamSelectionScreen(
                chapterId = data.chapterId,
                windowSize = windowSize,
                onBackPressed = { navController.navigateUp() },
                onStartFightClicked = { chapterId, teamId -> navController.navigate(
                    CombatScreenRoute(teamId = teamId, chapterId = chapterId)
                )}
            )
        }
        composable<CombatScreenRoute>{ backStackEntry ->
            val data: CombatScreenRoute = backStackEntry.toRoute()

            Log.d("CombatScreenRoute", "chapterId: ${data.chapterId}, teamId: ${data.teamId}")
            CombatScreen(
                chapterId = data.chapterId,
                teamId = data.teamId,
                windowSize = windowSize,
                onBackToTeamSelection = { navController.navigateUp() },
                onCombatFinished = { teamId, chapterId, combatResult ->
                    navController.navigate(CombatResultScreenRoute(teamId, chapterId, combatResult))
                }
            )
        }

        composable<CombatResultScreenRoute> { backStackEntry ->
            val data: CombatResultScreenRoute = backStackEntry.toRoute()

            CombatResultScreen(
                teamId = data.teamId,
                chapterId = data.chapterId,
                combatResult = data.combatResult,
                onBackPressed = { navController.popBackStack(HomeScreenRoute, true) },
                onReturnToHomePressed = { navController.popBackStack(HomeScreenRoute, true) }
            )
        }
    }
}