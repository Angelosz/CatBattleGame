package angelosz.catbattlegame.ui.playercollection

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.models.CollectionSmallCardData
import angelosz.catbattlegame.domain.models.OwnedCatDetailsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionViewModel(
    val catRepository: CatRepository,
    val abilityRepository: AbilityRepository,
    val playerAccountRepository: PlayerAccountRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<CollectionUiState>
    = MutableStateFlow(CollectionUiState())
    val uiState: StateFlow<CollectionUiState> = _uiState

    val smallCardData = _uiState.value.smallCardsData

    init {
        viewModelScope.launch {
            fetchOwnedCatsData()
        }
    }

    private suspend fun fetchOwnedCatsData() {
        val ownedCats = playerAccountRepository.getAllOwnedCats()
        val cats = catRepository.getCatsById(ownedCats.map { it.catId })
        val ownedCatsData = cats.mapIndexed() { index, cat ->
            CollectionSmallCardData(
                id = cat.id,
                image = cat.image,
                experience = ownedCats[index].experience,
                level = ownedCats[index].level,
            )
        }

        _uiState.update {
            it.copy(smallCardsData = ownedCatsData)
        }

        if(smallCardData.isNotEmpty()){
            fetchCatById(smallCardData.first().id)
        }
    }

    private suspend fun fetchCatById(catId: Int){
        val cat = catRepository.getCatById(catId)
        val ownedCatData = playerAccountRepository.getOwnedCatByCatId(catId)

        _uiState.update {
            it.copy(selectedCat = OwnedCatDetailsData(
                cat = cat.copy(
                    baseHealth = cat.baseHealth + ownedCatData.healthModifier,
                    baseAttack = cat.baseAttack + ownedCatData.attackModifier,
                    baseDefense = cat.baseDefense + ownedCatData.defenseModifier,
                    attackSpeed = cat.attackSpeed + ownedCatData.attackSpeedModifier
                ),
                abilities = abilityRepository.getCatAbilities(cat.id),
                experience = ownedCatData.experience,
                level = ownedCatData.level,
                evolutionCat = cat.nextEvolutionId?.let { evolution ->
                    Pair(evolution, catRepository.getCatById(evolution).name)
                },
                isElderOf = cat.isElderOf?.let { adultCatId ->
                    Pair(adultCatId, catRepository.getCatById(adultCatId).name)
                }
            ))
        }
    }

    fun selectCat(catId: Int) {
        viewModelScope.launch {
            try {
                fetchCatById(catId)
            } catch (e: Exception){
                Log.d("collection", e.message.toString())
            }
        }
    }

    fun changeView(toDetailView: Boolean) {
        _uiState.update {
            it.copy(isDetailView = toDetailView)
        }
    }

    fun deselectCat() {
        _uiState.update {
            it.copy(selectedCat = null)
        }
    }
}

/*

 */