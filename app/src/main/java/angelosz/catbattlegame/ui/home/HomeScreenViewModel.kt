package angelosz.catbattlegame.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val playerAccountRepository: PlayerAccountRepository): ViewModel() {

    private val _uiState: MutableStateFlow<HomeScreenUiState> = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    init {
        fetchPlayerData()
    }

    private fun fetchPlayerData() {
        viewModelScope.launch{
            val playerAccount = playerAccountRepository.getPlayerAccountAsFlow()
            playerAccount.collect{ data ->
                if(data != null)
                    _uiState.update {
                            it.copy(
                                gold = data.gold,
                                crystals = data.crystals
                            )
                    }
            }
        }
    }

}