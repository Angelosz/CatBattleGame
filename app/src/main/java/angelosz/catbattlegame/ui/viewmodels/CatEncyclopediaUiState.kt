package angelosz.catbattlegame.ui.viewmodels

import angelosz.catbattlegame.domain.enums.CollectionView
import angelosz.catbattlegame.domain.models.CatDetailsData
import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.domain.models.entities.Cat

data class CatEncyclopediaUiState(
    val cats: List<Cat> = emptyList(),
    val abilities: List<Ability> = emptyList(),
    val selectedCatData: CatDetailsData? = null,
    val selectedAbility: Ability? = null,
    val onDetailsView: Boolean = false,
    val collectionView: CollectionView = CollectionView.CATS
)