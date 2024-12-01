package angelosz.catbattlegame.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.models.OwnedCatData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatCollectionViewModel(
    val catRepository: CatRepository,
    val abilityRepository: AbilityRepository,
    val playerAccountRepository: PlayerAccountRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<CatCollectionUiState> = MutableStateFlow(CatCollectionUiState())
    val uiState: StateFlow<CatCollectionUiState> = _uiState

    init {
        viewModelScope.launch {
            fetchOwnedCatsData()
        }
    }

    private suspend fun fetchOwnedCatsData() {
        val ownedCats = playerAccountRepository.getAllOwnedCats()
        val cats = catRepository.getCatsById(ownedCats.map { it.catId })
        val ownedCatsData = cats.mapIndexed() { index, cat ->
            OwnedCatData(
                cat = cat,
                abilities = abilityRepository.getCatAbilities(cat.id),
                experience = ownedCats[index].experience,
                level = ownedCats[index].level
            )
        }
        _uiState.update {
            it.copy(ownedCats = ownedCatsData)
        }
    }
}