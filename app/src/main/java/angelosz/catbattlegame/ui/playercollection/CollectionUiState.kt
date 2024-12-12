package angelosz.catbattlegame.ui.playercollection

import angelosz.catbattlegame.domain.enums.CollectionView
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.CollectionSmallCardData
import angelosz.catbattlegame.domain.models.OwnedCatDetailsData

data class CollectionUiState(
    val smallCardsData: List<CollectionSmallCardData> = listOf(),
    val selectedCat: OwnedCatDetailsData? = null,
    val isDetailView: Boolean = false,
    val collectionView: CollectionView = CollectionView.CATS,
    val screenState: ScreenState = ScreenState.LOADING
)
