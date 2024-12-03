package angelosz.catbattlegame

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import angelosz.catbattlegame.ui.playercollection.CollectionViewModel
import angelosz.catbattlegame.ui.encyclopedia.EncyclopediaViewModel

object CatViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            EncyclopediaViewModel(
                getCatBattleApplication().container.catRepository,
                getCatBattleApplication().container.abilityRepository
            )
        }

        initializer {
            CollectionViewModel(
                getCatBattleApplication().container.catRepository,
                getCatBattleApplication().container.abilityRepository,
                getCatBattleApplication().container.playerRepository
            )
        }
    }
}

private fun CreationExtras.getCatBattleApplication(): CatBattleApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CatBattleApplication)