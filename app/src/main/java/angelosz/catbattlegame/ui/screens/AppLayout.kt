package angelosz.catbattlegame.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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