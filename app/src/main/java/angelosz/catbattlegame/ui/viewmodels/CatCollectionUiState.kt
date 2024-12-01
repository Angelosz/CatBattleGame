package angelosz.catbattlegame.ui.viewmodels

import angelosz.catbattlegame.domain.models.OwnedCatData

data class CatCollectionUiState(
    val ownedCats: List<OwnedCatData> = listOf()
)
