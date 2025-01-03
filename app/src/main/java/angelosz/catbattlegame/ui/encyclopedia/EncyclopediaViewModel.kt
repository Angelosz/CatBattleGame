package angelosz.catbattlegame.ui.encyclopedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.ui.collections.CollectionView
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.CatDetailsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EncyclopediaViewModel(
    val catRepository: CatRepository,
    val abilityRepository: AbilityRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<EncyclopediaUiState>
    = MutableStateFlow(EncyclopediaUiState())
    val uiState: StateFlow<EncyclopediaUiState> = _uiState

    private val catList: List<Cat>
        get() = _uiState.value.cats
    private val abilityList: List<Ability>
        get() = _uiState.value.abilities


    init {
        setupInitialData()
    }

    fun setupInitialData() {
        _uiState.update {
            it.copy(screenState = ScreenState.LOADING)
        }

        viewModelScope.launch {
            try{
                fetchAllCatData()
                fetchAllAbilityData()
                if (catList.isNotEmpty()) {
                    fetchCatDetails(_uiState.value.cats.first().id)
                }
                if (abilityList.isNotEmpty()) {
                    fetchAbilityData(abilityList.first().id)
                }

                _uiState.update {
                    it.copy(screenState = ScreenState.SUCCESS)
                }
            } catch (e: Exception){
                _uiState.update {
                    it.copy(screenState = ScreenState.FAILURE)
                }
            }
        }
    }

    private suspend fun fetchAllCatData() {
        _uiState.update {
            it.copy(cats = catRepository.getAllCats())
        }
    }

    private suspend fun fetchAllAbilityData() {
        _uiState.update {
            it.copy(abilities = abilityRepository.getAllPlayerAbilities())
        }
    }

    private suspend fun fetchCatDetails(catId: Int) {
        val cat = catRepository.getCatById(catId)
        val details = CatDetailsData(
            cat = cat,
            abilities = abilityRepository.getCatAbilities(catId),
            isElderOf = cat.isElderOf?.let { id -> catRepository.getCatById(id).name } ?: "",
            nextEvolutionName = cat.nextEvolutionId?.let { id -> catRepository.getCatById(id).name }
                ?: ""
        )
        _uiState.update{
            it.copy(selectedCatData = details)
        }
    }

    private suspend fun fetchAbilityData(abilityId: Int){
        _uiState.update {
            it.copy(selectedAbility = abilityRepository.getAbilityById(abilityId))
        }
    }

    fun updateSelectedCat(catId: Int){
        viewModelScope.launch {
            try {
                fetchCatDetails(catId)
            } catch (e: Exception){
                _uiState.update {
                    it.copy(screenState = ScreenState.FAILURE)
                }
            }
        }
    }

    fun updateSelectedAbility(abilityId: Int){
        viewModelScope.launch {
            try{
                fetchAbilityData(abilityId)
            } catch (e: Exception){
                _uiState.update {
                    it.copy(screenState = ScreenState.FAILURE)
                }
            }
        }
    }

    fun changeView(toDetailView: Boolean){
        _uiState.update {
            it.copy(onDetailsView = toDetailView)
        }
    }

    fun updateCollectionView(collectionView: CollectionView){
        _uiState.update {
            it.copy(collectionView = collectionView)
        }
    }

}