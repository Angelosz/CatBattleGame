package angelosz.catbattlegame.ui.archives.enemies_view

import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.archives.data.ArchiveEnemyData
import angelosz.catbattlegame.ui.archives.data.SimpleArchiveEnemyData

data class ArchiveEnemiesUiState (
    val screenState: ScreenState = ScreenState.INITIALIZING,

    val enemies: List<SimpleArchiveEnemyData> = emptyList(),
    val selectedEnemy: ArchiveEnemyData = ArchiveEnemyData(),
    val isEnemySelected: Boolean = false,

    val page: Int = 0,
    val pageLimit: Int = 9,
    val totalEnemyCats: Int = 9
)