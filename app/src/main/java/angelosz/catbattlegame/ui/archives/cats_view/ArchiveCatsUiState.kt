package angelosz.catbattlegame.ui.archives.cats_view

import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.archives.data.DetailedCatData
import angelosz.catbattlegame.ui.archives.data.SimpleCatData

data class ArchiveCatsUiState (
    val screenState: ScreenState = ScreenState.INITIALIZING,

    val playerCrystals: Int = 0,

    val cats: List<SimpleCatData> = emptyList(),
    val selectedCat: DetailedCatData = DetailedCatData(),
    val isCatSelected: Boolean = false,

    val pageLimit: Int = 9,
    val page: Int = 0,
    val totalNumberOfCats: Int = 9
)

