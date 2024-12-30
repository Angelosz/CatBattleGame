package angelosz.catbattlegame.ui.armory.cats_view

import angelosz.catbattlegame.domain.enums.ScreenState

data class ArmoryCatsUiState (
    val state: ScreenState = ScreenState.INITIALIZING,
    val cats: List<SimpleArmoryCatData> = emptyList(),
    val selectedCat: DetailedArmoryCatData = DetailedArmoryCatData(),
    val isDetailView: Boolean = false,

    val page: Int = 0,
    val totalNumberOfCats: Int = 0,
    val pageLimit: Int = 12,
)