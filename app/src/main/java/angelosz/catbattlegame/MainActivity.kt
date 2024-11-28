package angelosz.catbattlegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.lifecycleScope
import angelosz.catbattlegame.data.preloadData
import angelosz.catbattlegame.ui.screens.AppLayout
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            preloadData(applicationContext)
        }

        setContent {
            CatBattleGameTheme {
                AppLayout(windowSize = calculateWindowSizeClass(this).widthSizeClass)
            }
        }
    }


}