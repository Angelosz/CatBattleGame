package angelosz.catbattlegame

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import angelosz.catbattlegame.ui.viewmodels.CatDataViewModel

object CatViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            CatDataViewModel(getCatBattleApplication().container.repository)
        }
    }
}

private fun CreationExtras.getCatBattleApplication(): CatBattleApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CatBattleApplication)