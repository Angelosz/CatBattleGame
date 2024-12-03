package angelosz.catbattlegame.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import angelosz.catbattlegame.ui.playercollection.CollectionScreen
import angelosz.catbattlegame.ui.encyclopedia.EncyclopediaScreen
import angelosz.catbattlegame.ui.home.HomeScreen

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
                navigateToEncyclopedia = { navController.navigate(EncyclopediaScreenRoute) },
                navigateToCollection = { navController.navigate(CollectionScreenRoute) }
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
    }
}