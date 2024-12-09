package angelosz.catbattlegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.lifecycleScope
import angelosz.catbattlegame.data.database.AppDatabase
import angelosz.catbattlegame.domain.models.entities.PlayerAccount
import angelosz.catbattlegame.domain.models.entities.PlayerTeam
import angelosz.catbattlegame.domain.models.entities.PlayerTeamOwnedCat
import angelosz.catbattlegame.navigation.AppLayout
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            initializePlayerAccount()
        }

        setContent {
            CatBattleGameTheme {
                AppLayout(windowSize = calculateWindowSizeClass(this).widthSizeClass)
            }
        }
    }

    private suspend fun initializePlayerAccount() {
        val playerDao = AppDatabase.getInstance(applicationContext).playerDao()
        val existingAccount = playerDao.getPlayerAccount()
        if(existingAccount == null){
            playerDao.insertOrUpdateAccount(PlayerAccount())
            val ownedCatsIds = playerDao.getAllOwnedCats().map { it.id }
            /* Create first default team */
            playerDao.insertPlayerTeam(
                playerTeam = PlayerTeam(
                    id = 1,
                    name = "Default Team",
                )
            )
            /*  Populate first team */
            ownedCatsIds.forEachIndexed { index, catId -> playerDao.addCatToTeam(PlayerTeamOwnedCat(teamId = 1, ownedCatId = catId, index)) }
        }
    }


}