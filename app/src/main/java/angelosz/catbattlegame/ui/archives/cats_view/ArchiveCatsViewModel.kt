package angelosz.catbattlegame.ui.archives.cats_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.archives.data.DetailedCatData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArchiveCatsViewModel(
    private val catsRepository: CatRepository,
    private val abilitiesRepository: AbilityRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ArchiveCatsUiState())
    val uiState: StateFlow<ArchiveCatsUiState> = _uiState

    fun setup(pageLimit: Int = 9) {
        _uiState.update { it.copy(screenState = ScreenState.LOADING, pageLimit = pageLimit) }

        viewModelScope.launch {
            try {
                val cats = catsRepository.getSimpleCatDataFromPage(
                    limit = _uiState.value.pageLimit,
                    offset = _uiState.value.page * _uiState.value.pageLimit
                )
                val totalNumberOfCats = catsRepository.getCount()

                _uiState.update {
                    it.copy(
                        screenState = ScreenState.SUCCESS,
                        cats = cats,
                        totalNumberOfCats = totalNumberOfCats,
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    fun selectCat(id: Int) {
        viewModelScope.launch {
            try {
                val cat = catsRepository.getCatById(id)
                val catAbilities = abilitiesRepository.getCatAbilities(id)

                val details = DetailedCatData(
                    id = cat.id,
                    name = cat.name,
                    description = cat.description,
                    image = cat.image,
                    baseHealth = cat.baseHealth,
                    baseAttack = cat.baseAttack,
                    baseDefense = cat.baseDefense,
                    attackSpeed = cat.attackSpeed,
                    role = cat.role,
                    armorType = cat.armorType,
                    rarity = cat.rarity,
                    abilities = catAbilities,
                    evolutionLevel = cat.evolutionLevel,
                    nextEvolutionCat = if(cat.nextEvolutionId != null) catsRepository.getCatById(cat.nextEvolutionId) else null
                )

                _uiState.update {
                    it.copy(
                        selectedCat = details,
                        isCatSelected = true,
                    )
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    fun isNotLastPage(): Boolean = ((_uiState.value.page + 1) * _uiState.value.pageLimit) < _uiState.value.totalNumberOfCats

    fun goToPreviousPage() {
        val currentPage = _uiState.value.page
        if(currentPage > 0)
            _uiState.update {
                it.copy(
                    page = it.page - 1
                )
            }

        reloadSimpleCatData()
    }

    fun goToNextPage() {
        if(isNotLastPage())
            _uiState.update {
                it.copy(
                    page = it.page + 1
                )
            }
        reloadSimpleCatData()
    }

    private fun reloadSimpleCatData() {
        viewModelScope.launch {
            try {
                val cats = catsRepository.getSimpleCatDataFromPage(
                    limit = _uiState.value.pageLimit,
                    offset = _uiState.value.page * _uiState.value.pageLimit
                )

                _uiState.update {
                    it.copy(
                        cats = cats,
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }
}