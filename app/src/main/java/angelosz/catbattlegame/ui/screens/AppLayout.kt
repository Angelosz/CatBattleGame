package angelosz.catbattlegame.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppLayout(windowSize: WindowWidthSizeClass){
    Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
        HomeScreen(innerPadding)
    }
}
