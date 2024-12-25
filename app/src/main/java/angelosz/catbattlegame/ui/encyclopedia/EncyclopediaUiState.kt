package angelosz.catbattlegame.ui.encyclopedia

import angelosz.catbattlegame.domain.enums.CollectionView
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.CatDetailsData
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.Cat

data class EncyclopediaUiState(
    val cats: List<Cat> = emptyList(),
    val abilities: List<Ability> = emptyList(),
    val selectedCatData: CatDetailsData? = null,
    val selectedAbility: Ability? = null,
    val onDetailsView: Boolean = false,
    val collectionView: CollectionView = CollectionView.CATS,
    val screenState: ScreenState = ScreenState.LOADING
)