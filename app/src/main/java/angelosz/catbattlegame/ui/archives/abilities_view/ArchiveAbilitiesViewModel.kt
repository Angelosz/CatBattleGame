package angelosz.catbattlegame.ui.archives.abilities_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.domain.enums.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArchiveAbilitiesViewModel(
    private val abilityRepository: AbilityRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ArchiveAbilitiesUiState())
    val uiState: StateFlow<ArchiveAbilitiesUiState> = _uiState

    fun setup(pageLimit: Int = 9, selectedAbilityId: Int = 1, showAbility: Boolean = false) {
        _uiState.update { it.copy(screenState = ScreenState.LOADING, pageLimit = pageLimit) }

        viewModelScope.launch {
            try {
                val abilities = abilityRepository.getCatAbilitiesPage(
                    limit = _uiState.value.pageLimit,
                    offset = _uiState.value.page * _uiState.value.pageLimit
                )
                val totalNumberOfAbilities = abilityRepository.getCount()
                val ability = abilityRepository.getAbilityById(selectedAbilityId)

                _uiState.update {
                    it.copy(
                        screenState = ScreenState.SUCCESS,
                        abilities = abilities,
                        selectedAbility = ability,
                        isAbilitySelected = showAbility,
                        totalNumberOfAbilities = totalNumberOfAbilities,
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    private fun reloadPage(){
        viewModelScope.launch {
            try {
                val abilities = abilityRepository.getCatAbilitiesPage(
                    limit = _uiState.value.pageLimit,
                    offset = _uiState.value.page * _uiState.value.pageLimit
                )

                _uiState.update {
                    it.copy(abilities = abilities)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    fun isAbilitySelected(): Boolean = _uiState.value.isAbilitySelected

    fun returnToAbilityList(){
        _uiState.update {
            it.copy(
                isAbilitySelected = false
            )
        }
    }

    fun isNotLastPage(): Boolean = ((_uiState.value.page + 1) * _uiState.value.pageLimit) < _uiState.value.totalNumberOfAbilities

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

    fun selectAbility(id: Int) {
        viewModelScope.launch {
            try {
                val ability = abilityRepository.getAbilityById(id)
                _uiState.update {
                    it.copy(
                        selectedAbility = ability,
                        isAbilitySelected = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    fun viewModelHasBeenInitialized() = _uiState.value.screenState != ScreenState.INITIALIZING

}