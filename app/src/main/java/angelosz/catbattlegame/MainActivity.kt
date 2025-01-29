package angelosz.catbattlegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import angelosz.catbattlegame.data.database.AppDatabase
import angelosz.catbattlegame.data.datastore.DataStoreRepository
import angelosz.catbattlegame.navigation.AppLayout
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getInstance(applicationContext)
            val dataStore = DataStoreRepository(applicationContext)
            //db.clearAllTables()
            //dataStore.clearSelectedCampaign()
        }

        setContent {
            CatBattleGameTheme {
                AppLayout(windowSize = calculateWindowSizeClass(this).widthSizeClass)
            }
        }
    }
}