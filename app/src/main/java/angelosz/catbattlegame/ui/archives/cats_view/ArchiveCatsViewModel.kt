package angelosz.catbattlegame.ui.archives.cats_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.entities.OwnedCat
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.archives.data.DetailedCatData
import angelosz.catbattlegame.utils.GameConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArchiveCatsViewModel(
    private val catsRepository: CatRepository,
    private val abilitiesRepository: AbilityRepository,
    private val playerAccountRepository: PlayerAccountRepository
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
                val playerCrystals = playerAccountRepository.getCrystalsAmount()
                val selectedCat = getDetailedCatData(cats.first().id)

                _uiState.update {
                    it.copy(
                        screenState = ScreenState.SUCCESS,
                        playerCrystals = playerCrystals,
                        selectedCat = selectedCat,
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
                val cat = getDetailedCatData(id)

                _uiState.update {
                    it.copy(
                        selectedCat = cat,
                        isCatSelected = true,
                    )
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    private suspend fun getDetailedCatData(catId: Int): DetailedCatData{
        val cat = catsRepository.getCatById(catId)
        val catAbilities = abilitiesRepository.getCatAbilities(catId)

        return DetailedCatData(
            id = cat.id,
            name = cat.name,
            title = cat.title,
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
            nextEvolutionCat = if(cat.nextEvolutionId != null) catsRepository.getCatById(cat.nextEvolutionId) else null,
            playerOwnsIt = playerAccountRepository.ownsCat(cat.id)
        )
    }

    fun isCatSelected(): Boolean = _uiState.value.isCatSelected

    fun returnToCatList() {
        _uiState.update {
            it.copy(
                isCatSelected = false
            )
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

    fun catWasPurchased(catId: Int) {
        viewModelScope.launch {
            try{
                val cat = catsRepository.getCatById(catId)
                val playerCrystals = playerAccountRepository.getCrystalsAmount()
                val crystalsCost = GameConstants.GET_CAT_CRYSTAL_COST(cat.rarity)
                if(crystalsCost <= playerCrystals && !playerAccountRepository.ownsCat(catId)){
                    playerAccountRepository.reduceCrystals(crystalsCost)
                    playerAccountRepository.insertOwnedCat(OwnedCat(catId = cat.id))
                    selectCat(cat.id)
                }
            } catch(e: Exception){
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }
}