package angelosz.catbattlegame.ui.teamselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.teambuilder.TeamData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeamSelectionScreenViewModel(private val playerAccountRepository: PlayerAccountRepository): ViewModel() {
    private val _uiState = MutableStateFlow(TeamSelectionScreenUiState())
    val uiState: StateFlow<TeamSelectionScreenUiState> = _uiState

    init {
        setupInitialData()
    }

    fun setupInitialData() {
        _uiState.update {
            it.copy(
                screenState = ScreenState.LOADING
            )
        }
        viewModelScope.launch{
            try {
                val teams = playerAccountRepository.getAllPlayerTeams().map { team ->
                    TeamData(
                        teamId = team.id,
                        teamName = team.name,
                        cats = playerAccountRepository.getTeamData(team.id)
                    )
                }
                _uiState.update {
                    it.copy(
                        teams = teams,
                        screenState = ScreenState.SUCCESS
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.FAILURE
                    )
                }
            }
        }
    }

    fun selectTeam(teamId: Long) {
        _uiState.update {
            it.copy(
                teamId = teamId,
            )
        }
    }
}
