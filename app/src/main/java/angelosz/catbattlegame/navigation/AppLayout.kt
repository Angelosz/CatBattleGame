package angelosz.catbattlegame.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.archives.ArchivesScreen
import angelosz.catbattlegame.ui.armory.ArmoryScreen
import angelosz.catbattlegame.ui.armory.enums.CollectionsView
import angelosz.catbattlegame.ui.campaign.CampaignScreen
import angelosz.catbattlegame.ui.combat.CombatScreen
import angelosz.catbattlegame.ui.combatreward.CombatResultScreen
import angelosz.catbattlegame.ui.components.RoundedImageButton
import angelosz.catbattlegame.ui.home.HomeScreen
import angelosz.catbattlegame.ui.teamselection.TeamSelectionScreen
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

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
                navigateToArchive = { navController.navigate(ArchiveScreenRoute(CollectionsView.CATS)) },
                navigateToCollections = { navController.navigate(ArmoryScreenRoute(CollectionsView.CATS)) },
            )
        }
        composable<ArmoryScreenRoute> { backStackEntry ->
            val data: ArmoryScreenRoute = backStackEntry.toRoute()
            ArmoryScreen(
                windowSize = windowSize,
                returnToMainMenu = { navController.navigateUp() },
                selectCollection = data.selectedCollection
            )
        }
        composable<ArchiveScreenRoute> {
            ArchivesScreen(
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
                onBackPressed = { navController.popBackStack(HomeScreenRoute, false) },
                onReturnToHomePressed = { navController.popBackStack(HomeScreenRoute, false) }
            )
        }
    }
}

@Composable
fun TopCurrencyBar(gold: Int, crystals: Int){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.topappborder_1028_257),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.goldcoins_128),
                contentDescription = "Gold coins",
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "$gold",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                painter = painterResource(R.drawable.crystals_128),
                contentDescription = "Crystals",
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "$crystals",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )

        }
    }
}

@Composable
fun BottomGeneralBar(navigateToBattleChests: () -> Unit, navigateToTeamBuild: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.topappborder_1028_257),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            RoundedImageButton(
                onClick = navigateToTeamBuild,
                innerImage = R.drawable.teams_button_256,
            )
            RoundedImageButton(
                onClick = navigateToBattleChests,
                innerImage = R.drawable.battlechest_256,
            )

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopCurrencyBarPreview(){
    CatBattleGameTheme {
        Scaffold(
            topBar = {
                TopCurrencyBar(gold = 0, crystals = 0)
            },
            bottomBar = {
                BottomGeneralBar(navigateToBattleChests = {}, navigateToTeamBuild = {})
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}