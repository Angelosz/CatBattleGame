package angelosz.catbattlegame.ui.archives.enemies_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.EnemyCatRepository
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.archives.data.SimpleArchiveEnemyData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArchiveEnemiesViewModel(private val enemyCatRepository: EnemyCatRepository): ViewModel() {
    private val _uiState = MutableStateFlow(ArchiveEnemiesUiState())
    val uiState: StateFlow<ArchiveEnemiesUiState> = _uiState

    fun setup(pageLimit: Int = 9) {
        _uiState.value = ArchiveEnemiesUiState( screenState = ScreenState.LOADING, pageLimit = pageLimit )

        viewModelScope.launch {
            try{
                val enemies = fetchPage()

                _uiState.update{ it.copy(
                    enemies = enemies,
                    totalEnemyCats = enemyCatRepository.getCount(),
                    screenState = ScreenState.SUCCESS
                )}
            } catch (e: Exception){
                _uiState.update{ it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    private suspend fun fetchPage(): List<SimpleArchiveEnemyData> {
        return enemyCatRepository.getSimplifiedArchiveEnemiesPage(
            limit = _uiState.value.pageLimit,
            offset = _uiState.value.page * _uiState.value.pageLimit
        )
    }

    private fun reloadPage() {
        viewModelScope.launch {
            try {
                val enemies = fetchPage()

                _uiState.update {
                    it.copy(enemies = enemies)
                }
            } catch(e: Exception){
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    fun selectEnemy(id: Long) {
        viewModelScope.launch {
            try {
                val enemy = enemyCatRepository.getArchiveEnemyData(id)

                _uiState.update {
                    it.copy(
                        selectedEnemy = enemy,
                        isEnemySelected = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    fun isEnemySelected(): Boolean = _uiState.value.isEnemySelected

    fun returnToEnemyList(){
        _uiState.update {
            it.copy(
                isEnemySelected = false
            )
        }
    }

    fun isNotLastPage(): Boolean = ((_uiState.value.page + 1) * _uiState.value.pageLimit) < _uiState.value.totalEnemyCats

    fun goToPreviousPage() {
        val currentPage = _uiState.value.page
        if(currentPage > 0)
            _uiState.update {
                it.copy(
                    page = it.page - 1
                )
            }

        reloadPage()
    }

    fun goToNextPage() {
        if(isNotLastPage())
            _uiState.update {
                it.copy(
                    page = it.page + 1
                )
            }
        reloadPage()
    }
}