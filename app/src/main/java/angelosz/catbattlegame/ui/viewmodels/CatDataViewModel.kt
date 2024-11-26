package angelosz.catbattlegame.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.OfflineCatDaoRepository
import angelosz.catbattlegame.domain.models.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatDataViewModel(private val catRepository: OfflineCatDaoRepository): ViewModel() {
    private val _cats = MutableStateFlow<List<Cat>>(emptyList())
    val cats: StateFlow<List<Cat>> = _cats

    init{
        fetchAllCats()
    }

    private fun fetchAllCats() {
        viewModelScope.launch {
            _cats.value = catRepository.getAllCats()
        }
    }
}