package angelosz.catbattlegame

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import angelosz.catbattlegame.ui.viewmodels.CatCollectionViewModel
import angelosz.catbattlegame.ui.viewmodels.CatEncyclopediaViewModel

object CatViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            CatEncyclopediaViewModel(
                getCatBattleApplication().container.catRepository,
                getCatBattleApplication().container.abilityRepository
            )
        }

        initializer {
            CatCollectionViewModel(
                getCatBattleApplication().container.catRepository,
                getCatBattleApplication().container.abilityRepository,
                getCatBattleApplication().container.playerRepository
            )
        }
    }
}

private fun CreationExtras.getCatBattleApplication(): CatBattleApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CatBattleApplication)