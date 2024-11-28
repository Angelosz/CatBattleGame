package angelosz.catbattlegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import angelosz.catbattlegame.ui.screens.AppLayout
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatBattleGameTheme {
                AppLayout(windowSize = calculateWindowSizeClass(this).widthSizeClass)
            }
        }
    }


}