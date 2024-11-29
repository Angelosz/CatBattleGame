package angelosz.catbattlegame.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.domain.enums.CollectionView
import angelosz.catbattlegame.domain.models.CatDetailsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatEncyclopediaViewModel(
    val catRepository: CatRepository,
    val abilityRepository: AbilityRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<CatEncyclopediaUiState> = MutableStateFlow(
        CatEncyclopediaUiState()
    )
    val uiState: StateFlow<CatEncyclopediaUiState> = _uiState

    private val catList = _uiState.value.cats
    private val abilityList = _uiState.value.abilities


    init {
        viewModelScope.launch {
            fetchAllCatData()
            fetchAllAbilityData()
            if(catList.isNotEmpty()) {
                fetchCatDetails(catList[0].id)
            }
            if(abilityList.isNotEmpty()){
                fetchAbilityData(abilityList[0].id)
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
            it.copy(abilities = abilityRepository.getAllAbilities())
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
            fetchCatDetails(catId)
        }
    }

    fun updateSelectedAbility(abilityId: Int){
        viewModelScope.launch {
            fetchAbilityData(abilityId)
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

    fun deselectAllData() {
        _uiState.update {
            it.copy(
                selectedCatData = null,
                selectedAbility = null
                )
        }
    }
}