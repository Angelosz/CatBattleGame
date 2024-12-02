package angelosz.catbattlegame.ui.viewmodels

import angelosz.catbattlegame.domain.models.CollectionSmallCardData
import angelosz.catbattlegame.domain.models.OwnedCatDetailsData

data class CatCollectionUiState(
    val smallCardsData: List<CollectionSmallCardData> = listOf(),
    val selectedCat: OwnedCatDetailsData? = null,
    val isDetailView: Boolean = false
) {
}
