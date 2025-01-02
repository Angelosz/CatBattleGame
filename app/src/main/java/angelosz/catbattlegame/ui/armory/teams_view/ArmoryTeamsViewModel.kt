package angelosz.catbattlegame.ui.armory.teams_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.entities.PlayerTeam
import angelosz.catbattlegame.data.entities.PlayerTeamOwnedCat
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.data.ArmoryTeam
import angelosz.catbattlegame.ui.armory.data.SimpleArmoryCatData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArmoryTeamsViewModel(val playerAccountRepository: PlayerAccountRepository): ViewModel() {
    private val _uiState = MutableStateFlow(ArmoryTeamsUiState())
    val uiState: StateFlow<ArmoryTeamsUiState> = _uiState

    fun setup(pageLimit: Int) {
        _uiState.update { it.copy( state = ScreenState.LOADING, pageLimit = pageLimit ) }

        viewModelScope.launch {
            try{
                val teams = fetchAllPlayerTeams()
                val selectedTeam = if(teams.isEmpty()) ArmoryTeam(0, "No Teams", emptyList()) else teams.first()
                val cats = fetchCatsFromCurrentPage()

                delay(100)

                _uiState.update {
                    it.copy(
                        teams = teams,
                        selectedTeam = selectedTeam,
                        cats = cats,
                        totalNumberOfCats = playerAccountRepository.getOwnedCatsCount(),
                        state = ScreenState.SUCCESS
                    )
                }
            } catch (e: Exception){
                _uiState.update { it.copy( state = ScreenState.FAILURE ) }
            }
        }
    }

    private suspend fun fetchCatsFromCurrentPage(): List<SimpleArmoryCatData> {
        return playerAccountRepository.getSimpleArmoryCatsDataPage(_uiState.value.pageLimit, _uiState.value.pageLimit * _uiState.value.page)
    }

    private suspend fun fetchAllPlayerTeams(): List<ArmoryTeam> {
        val teams = mutableListOf<ArmoryTeam>()
        playerAccountRepository.getAllPlayerTeams().forEach { team ->
            teams.add(
                ArmoryTeam(
                    teamId = team.id,
                    teamName = team.name,
                    cats = playerAccountRepository.getSimpleCatsDataFromTeam(team.id)
                )
            )
        }
        return teams
    }

    fun deleteTeam(teamId: Long) {
        viewModelScope.launch {
            try {
                playerAccountRepository.clearTeam(teamId)
                playerAccountRepository.deleteTeamById(teamId)
                reloadTeams()
            } catch (e: Exception){
                _uiState.update { it.copy( state = ScreenState.FAILURE ) }
            }
        }
    }

    private suspend fun reloadTeams() {
        val teams = fetchAllPlayerTeams()
        _uiState.update {
            it.copy(
                teams = teams
            )
        }
    }

    fun createTeam() {
        val team = ArmoryTeam(
            teamId = getHighestTeamId() + 1,
            teamName = "New Team",
            cats = emptyList()
        )
        _uiState.update {
            it.copy(
                selectedTeam = team,
                isTeamSelected = true
            )
        }
    }

    private fun getHighestTeamId(): Long {
        var highestId = 0L
        _uiState.value.teams.forEach {
            if(it.teamId > highestId) highestId = it.teamId
        }
        return highestId
    }

    fun selectTeam(teamId: Long) {
        val team = _uiState.value.teams.find { it.teamId == teamId }
        if(team != null){
            _uiState.update {
                it.copy(
                    selectedTeam =  team,
                    isTeamSelected = true,
                )
            }
        }
    }

    fun addCatToTeam(cat: SimpleArmoryCatData) {
        if(teamIsFull() || catIsInTeam(cat)) return
        _uiState.update {
            it.copy(
                selectedTeam = it.selectedTeam.copy(
                    cats = it.selectedTeam.cats.plus(cat)
                )
            )
        }
    }

    private fun catIsInTeam(cat: SimpleArmoryCatData) =
        _uiState.value.selectedTeam.cats.contains(cat)

    private fun teamIsFull() = _uiState.value.selectedTeam.cats.size >= 4

    fun removeCatFromTeam(newCat: SimpleArmoryCatData) {
        if(_uiState.value.selectedTeam.cats.isEmpty()) return
        _uiState.update {
            it.copy(
                selectedTeam = it.selectedTeam.copy(
                    cats = it.selectedTeam.cats.filterNot { cat -> cat.id == newCat.id }
                )
            )
        }
    }

    fun saveTeam() {
        viewModelScope.launch {
            try{
                val team = _uiState.value.selectedTeam
                val teamExists = playerAccountRepository.teamExists(team.teamId)

                if(team.cats.isEmpty()) {
                    if(teamExists) deleteTeam(team.teamId)
                } else {
                    if(playerAccountRepository.teamExists(team.teamId)){
                        playerAccountRepository.clearTeam(team.teamId)
                        playerAccountRepository.updatePlayerTeam(
                            PlayerTeam(
                                id = team.teamId,
                                name = team.teamName
                            )
                        )
                    } else {
                        playerAccountRepository.insertPlayerTeam(
                            PlayerTeam(
                                id = team.teamId,
                                name = team.teamName
                            )
                        )
                    }

                    val cats = _uiState.value.selectedTeam.cats.toSet()
                    cats.forEachIndexed{ index, cat ->
                        playerAccountRepository.addCatToTeam(
                            PlayerTeamOwnedCat(team.teamId.toInt(), cat.id, index)
                        )
                    }
                }

                reloadTeams()

            } catch (e: Exception){
                _uiState.update { it.copy( state = ScreenState.FAILURE ) }
            }
        }
    }

    fun renameTeam(name: String) {
        _uiState.update {
            it.copy(
                selectedTeam = it.selectedTeam.copy(
                    teamName = name
                )
            )
        }
    }

    fun hasTeamSelected(): Boolean = _uiState.value.isTeamSelected

    fun deselectTeam() {
        _uiState.update {
            it.copy(
                isTeamSelected = false
            )
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

    private fun reloadCatsSimpleData() {
        viewModelScope.launch {
            try{
                _uiState.update {
                    it.copy(
                        cats = fetchCatsFromCurrentPage()
                    )
                }
        } catch (e: Exception){
                _uiState.update { it.copy( state = ScreenState.FAILURE ) }
            }
        }
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
}