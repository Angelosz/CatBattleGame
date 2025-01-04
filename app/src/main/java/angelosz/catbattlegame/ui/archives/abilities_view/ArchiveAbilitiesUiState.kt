package angelosz.catbattlegame.ui.archives.abilities_view

import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.domain.enums.ScreenState

data class ArchiveAbilitiesUiState (
    val screenState: ScreenState = ScreenState.INITIALIZING,

    val abilities: List<Ability> = emptyList(),
    val selectedAbility: Ability = Ability(0),
    val isAbilitySelected: Boolean = false,

    val pageLimit: Int = 9,
    val page: Int = 0,
    val totalNumberOfAbilities: Int = 9
)