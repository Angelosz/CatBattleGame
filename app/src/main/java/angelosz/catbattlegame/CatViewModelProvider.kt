package angelosz.catbattlegame

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import angelosz.catbattlegame.ui.battlechests.BattleChestsViewModel
import angelosz.catbattlegame.ui.combat.CampaignScreenViewModel
import angelosz.catbattlegame.ui.encyclopedia.EncyclopediaViewModel
import angelosz.catbattlegame.ui.home.HomeScreenViewModel
import angelosz.catbattlegame.ui.playercollection.CollectionViewModel
import angelosz.catbattlegame.ui.teambuilder.TeamBuilderViewModel

object CatViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(
                getCatBattleApplication().container.playerRepository
            )
        }

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

        initializer {
            BattleChestsViewModel(
                getCatBattleApplication().container.catRepository,
                getCatBattleApplication().container.playerRepository,
                getCatBattleApplication().container.battleChestRepository
            )
        }

        initializer {
            TeamBuilderViewModel(
                getCatBattleApplication().container.catRepository,
                getCatBattleApplication().container.playerRepository,
                getCatBattleApplication().container.abilityRepository,
            )
        }

        initializer {
            CampaignScreenViewModel(
                getCatBattleApplication().container.campaignRepository
            )
        }
    }
}

private fun CreationExtras.getCatBattleApplication(): CatBattleApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CatBattleApplication)