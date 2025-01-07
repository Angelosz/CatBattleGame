package angelosz.catbattlegame.ui.armory.cats_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.data.DetailedArmoryCatData
import angelosz.catbattlegame.ui.armory.data.SimpleArmoryCatData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArmoryCatsViewModel(
    private val catRepository: CatRepository,
    val playerAccountRepository: PlayerAccountRepository,
    private val abilityRepository: AbilityRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ArmoryCatsUiState())
    val uiState: StateFlow<ArmoryCatsUiState> = _uiState.asStateFlow()


    fun setup(pageLimit: Int = 9) {
        _uiState.update { it.copy( state = ScreenState.LOADING ) }

        viewModelScope.launch {
            try {
                _uiState.update { it.copy( pageLimit = pageLimit ) }
                val cats = fetchCatsSimpleData()
                val selectedCat = fetchDetailedCatData(cats.first().id)
                val totalNumberOfCats = playerAccountRepository.getOwnedCatsCount()

                delay(100)

                _uiState.update {
                    it.copy(
                        cats = cats,
                        selectedCat = selectedCat,
                        state = ScreenState.SUCCESS,
                        totalNumberOfCats = totalNumberOfCats
                    )
                }
            } catch (e: Exception){
                _uiState.update { it.copy(state = ScreenState.FAILURE) }
            }
        }
    }

    private suspend fun fetchCatsSimpleData(): List<SimpleArmoryCatData> {
        return playerAccountRepository.getSimpleArmoryCatsDataPage(
            limit = _uiState.value.pageLimit,
            offset = _uiState.value.page * _uiState.value.pageLimit
        )
    }

    private suspend fun fetchDetailedCatData(id: Int): DetailedArmoryCatData {
        val ownedCat = playerAccountRepository.getOwnedCatByCatId(id)
        val cat = catRepository.getCatById(id)
        val abilities = abilityRepository.getCatAbilities(id)

        return DetailedArmoryCatData(
            id = cat.id,
            name = cat.name,
            title = cat.title,
            image = cat.image,
            description = cat.description,
            armorType = cat.armorType,
            role = cat.role,
            health = cat.baseHealth + ownedCat.healthModifier,
            defense = cat.baseDefense + ownedCat.defenseModifier,
            attack = cat.baseAttack + ownedCat.attackModifier,
            speed = cat.attackSpeed + ownedCat.attackSpeedModifier,
            rarity = cat.rarity,
            abilities = abilities,
            level = ownedCat.level,
            experience = ownedCat.experience,
        )
    }

    private fun reloadCatsSimpleData() {
        viewModelScope.launch {
            try{
                _uiState.update {
                    it.copy(
                        cats = fetchCatsSimpleData()
                    )
                }
            } catch (e: Exception){
                _uiState.update { it.copy(state = ScreenState.FAILURE) }
            }
        }
    }


    fun isNotLastPage(): Boolean {
        return ((_uiState.value.page + 1) * _uiState.value.pageLimit) < _uiState.value.totalNumberOfCats
    }

    fun goToPreviousPage() {
        val currentPage = _uiState.value.page

        if(currentPage > 0){
            _uiState.update {
                it.copy( page = currentPage - 1 )
            }
        }

        reloadCatsSimpleData()
    }

    fun goToNextPage() {
        val currentPage = _uiState.value.page

        if(isNotLastPage()){
            _uiState.update {
                it.copy( page = currentPage + 1 )
            }
        }

        reloadCatsSimpleData()
    }

    fun exitDetailView() {
        _uiState.update {
            it.copy(
                isDetailView = false
            )
        }
    }

    fun selectCat(id: Int) {
        viewModelScope.launch {
            try {
                val selectedCat = fetchDetailedCatData(id)

                _uiState.update {
                    it.copy(
                        selectedCat = selectedCat,
                        isDetailView = true,
                    )
                }
            } catch (e: Exception){
                _uiState.update { it.copy(state = ScreenState.FAILURE) }
            }
        }
    }

    fun reloadDataIfAlreadyInitialized() {
        viewModelScope.launch {
            try {
                if(_uiState.value.state != ScreenState.INITIALIZING){
                    reloadCatsSimpleData()
                }
            } catch (e: Exception){
                _uiState.update { it.copy( state = ScreenState.FAILURE ) }
            }
        }
    }
}
