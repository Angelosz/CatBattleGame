package angelosz.catbattlegame.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import angelosz.catbattlegame.ui.battlechests.BattleChestsScreen
import angelosz.catbattlegame.ui.combat.CombatMenuScreen
import angelosz.catbattlegame.ui.encyclopedia.EncyclopediaScreen
import angelosz.catbattlegame.ui.home.HomeScreen
import angelosz.catbattlegame.ui.playercollection.CollectionScreen
import angelosz.catbattlegame.ui.teambuilder.TeamBuilderScreen

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
                onPlayButtonClick = { navController.navigate(CombatMenuScreenRoute) },
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
        composable<CombatMenuScreenRoute>{
            CombatMenuScreen(
                windowSize = windowSize,
                onBackButtonPressed = { navController.navigateUp() }
            )
        }
    }
}