package angelosz.catbattlegame

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import angelosz.catbattlegame.ui.archives.ArchiveScreenViewModel
import angelosz.catbattlegame.ui.archives.abilities_view.ArchiveAbilitiesViewModel
import angelosz.catbattlegame.ui.archives.cats_view.ArchiveCatsViewModel
import angelosz.catbattlegame.ui.archives.enemies_view.ArchiveEnemiesViewModel
import angelosz.catbattlegame.ui.armory.ArmoryScreenViewModel
import angelosz.catbattlegame.ui.armory.battlechest_view.ArmoryBattleChestViewModel
import angelosz.catbattlegame.ui.armory.cats_view.ArmoryCatsViewModel
import angelosz.catbattlegame.ui.armory.teams_view.ArmoryTeamsViewModel
import angelosz.catbattlegame.ui.campaign.CampaignScreenViewModel
import angelosz.catbattlegame.ui.combat.CombatScreenViewModel
import angelosz.catbattlegame.ui.combat.combatreward.CombatResultViewModel
import angelosz.catbattlegame.ui.combat.teamselection.TeamSelectionScreenViewModel
import angelosz.catbattlegame.ui.home.HomeScreenViewModel

object CatViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(
                getCatBattleApplication().container.playerRepository,
                getCatBattleApplication().container.campaignRepository,
                getCatBattleApplication().container.catRepository,
                getCatBattleApplication().container.abilityRepository,
                getCatBattleApplication().container.enemyCatRepository,
                getCatBattleApplication().container.battleChestRepository
            )
        }

        /* Campaign Selection */
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

        /* Combat */
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
                getCatBattleApplication().container.battleChestRepository,
                getCatBattleApplication().container.catRepository,
                getCatBattleApplication().container.enemyCatRepository,
            )
        }

        /* Archive ViewModels */
        initializer {
            ArchiveScreenViewModel()
        }

        initializer {
            ArchiveCatsViewModel(
                getCatBattleApplication().container.catRepository,
                getCatBattleApplication().container.abilityRepository,
                getCatBattleApplication().container.playerRepository
            )
        }

        initializer {
            ArchiveAbilitiesViewModel(
                getCatBattleApplication().container.abilityRepository
            )
        }

        initializer {
            ArchiveEnemiesViewModel(
                getCatBattleApplication().container.enemyCatRepository
            )
        }

        /* Armory ViewModels */
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

        initializer {
            ArmoryBattleChestViewModel(
                getCatBattleApplication().container.catRepository,
                getCatBattleApplication().container.playerRepository,
                getCatBattleApplication().container.battleChestRepository
            )
        }
    }
}

private fun CreationExtras.getCatBattleApplication(): CatBattleApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CatBattleApplication)