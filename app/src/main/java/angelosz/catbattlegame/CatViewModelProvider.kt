package angelosz.catbattlegame

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import angelosz.catbattlegame.ui.armory.cats_view.ArmoryCatsViewModel
import angelosz.catbattlegame.ui.battlechests.BattleChestsViewModel
import angelosz.catbattlegame.ui.campaign.CampaignScreenViewModel
import angelosz.catbattlegame.ui.combat.CombatScreenViewModel
import angelosz.catbattlegame.ui.combatreward.CombatResultViewModel
import angelosz.catbattlegame.ui.encyclopedia.EncyclopediaViewModel
import angelosz.catbattlegame.ui.home.HomeScreenViewModel
import angelosz.catbattlegame.ui.playercollection.CollectionViewModel
import angelosz.catbattlegame.ui.teambuilder.TeamBuilderViewModel
import angelosz.catbattlegame.ui.teamselection.TeamSelectionScreenViewModel

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
                getCatBattleApplication().container.campaignRepository,
                getCatBattleApplication().container.enemyCatRepository
            )
        }

        initializer() {
            TeamSelectionScreenViewModel(
                getCatBattleApplication().container.playerRepository
            )
        }

        initializer(){
            CombatScreenViewModel(
                getCatBattleApplication().container.playerRepository,
                getCatBattleApplication().container.campaignRepository,
                getCatBattleApplication().container.enemyCatRepository,
                getCatBattleApplication().container.abilityRepository,
                getCatBattleApplication().container.catRepository
            )
        }

        initializer{
            CombatResultViewModel(
                getCatBattleApplication().container.campaignRepository,
                getCatBattleApplication().container.playerRepository,
                getCatBattleApplication().container.battleChestRepository
            )
        }
        initializer {
            ArmoryScreenViewModel()
        }

        initializer {
            ArmoryCatsViewModel(
                getCatBattleApplication().container.catRepository,
                getCatBattleApplication().container.playerRepository,
                getCatBattleApplication().container.abilityRepository
            )
        }

        initializer {
            ArmoryTeamsViewModel(
                getCatBattleApplication().container.playerRepository
            )
        }
    }
}

private fun CreationExtras.getCatBattleApplication(): CatBattleApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CatBattleApplication)