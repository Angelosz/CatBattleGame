package angelosz.catbattlegame.ui.teambuilder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.OwnedCatDetailsData
import angelosz.catbattlegame.data.entities.PlayerTeam
import angelosz.catbattlegame.data.entities.PlayerTeamOwnedCat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeamBuilderViewModel(
    private val catRepository: CatRepository,
    private val playerAccountRepository: PlayerAccountRepository,
    private val abilityRepository: AbilityRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<TeamBuilderUiState>
    = MutableStateFlow( TeamBuilderUiState() )
    val uiState: StateFlow<TeamBuilderUiState> = _uiState

    val paginationLimit = 9

    init {
        setupInitialData()
    }

    fun setupInitialData() {
        _uiState.update {
            it.copy(
                screenState = ScreenState.LOADING
            )
        }

        viewModelScope.launch {
            try {
                fetchAllPlayerTeams()
                fetchCatPage()
                fetchOwnedCatCount()

                _uiState.update {
                    it.copy(
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

    private suspend fun fetchOwnedCatCount() {
        _uiState.update {
            it.copy(
                ownedCatCount = playerAccountRepository.getCount()
            )
        }
    }

    private suspend fun fetchCatPage() {
        val ownedCats = playerAccountRepository
            .getPaginatedOwnedCats(
                limit = paginationLimit,
                offset = paginationLimit * (_uiState.value.catListPage - 1)
            )
        val listOfBasicCatData: List<BasicCatData> =
            catRepository.getCatsById( ownedCats.map { ownedCat -> ownedCat.catId } )
            .map { cat ->
                BasicCatData(
                    catId = cat.id,
                    image = cat.image
                )
            }
        if(listOfBasicCatData.isNotEmpty()) selectCat(listOfBasicCatData.first().catId)
        _uiState.update {
            it.copy(
                pageCatsData = listOfBasicCatData,
            )
        }
    }

    private suspend fun fetchAllPlayerTeams() {
        val teams = playerAccountRepository.getAllPlayerTeams()
        val teamsData = mutableListOf<TeamData>()

        for (team in teams) {
            teamsData.add(
                TeamData(
                    teamId = team.id,
                    teamName = team.name,
                    cats = playerAccountRepository.getTeamData(team.id)
                )
            )
        }

        _uiState.update {
            it.copy(
                teams = teamsData
            )
        }
    }

    fun selectTeam(teamId: Long){
        val team = _uiState.value.teams.find { it.teamId == teamId }
        if(team != null){
            _uiState.update {
                it.copy(
                    selectedTeam =  team,
                    teamIsSelected = true
                )
            }
        }
    }

    fun createNewTeam(){
        viewModelScope.launch {
            try {
                val teamId = playerAccountRepository.insertPlayerTeam(
                    PlayerTeam(
                        name = "New Team"
                    )
                )
                _uiState.update {
                    it.copy(
                        selectedTeam = TeamData(
                            teamId = teamId,
                            teamName = "New Team",
                            cats = listOf()
                        ),
                        teamIsSelected = true
                    )
                }
            } catch (e: Exception){
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.FAILURE
                    )
                }
            }
        }
    }

    fun deleteTeam(teamId: Long) {
        viewModelScope.launch {
            try {
                val team = playerAccountRepository.getPlayerTeamById(teamId)
                playerAccountRepository.deleteTeam(team)
                fetchAllPlayerTeams()
            } catch (e: Exception){
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.FAILURE
                    )
                }
            }
        }
    }

    fun goBackToTeamList(){
        viewModelScope.launch {
            try{
                val selectedTeam = _uiState.value.selectedTeam
                playerAccountRepository.clearTeam(selectedTeam.teamId)
                val catIds = selectedTeam.cats.map { it.catId }
                if(catIds.isNotEmpty()) {
                    catIds.forEachIndexed { index, catId -> playerAccountRepository.addCatToTeam(
                        playerTeamOwnedCat = PlayerTeamOwnedCat(selectedTeam.teamId.toInt(), catId, position = index)
                    )}
                    playerAccountRepository.insertPlayerTeam(PlayerTeam(selectedTeam.teamId, selectedTeam.teamName))
                } else {
                    playerAccountRepository.deleteTeambyId(selectedTeam.teamId)
                }
                fetchAllPlayerTeams()
                _uiState.update {
                    it.copy(
                        selectedTeam = TeamData(
                            teamId = 1,
                            teamName = "Default Team",
                            cats = listOf()
                        ),
                        teamIsSelected = false
                    )
                }
            } catch (e: Exception){
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.FAILURE
                    )
                }
            }
        }
    }

    fun selectCat(catId: Int){
        viewModelScope.launch {
            try {
                val ownedCat = playerAccountRepository.getOwnedCatByCatId(catId)
                val cat = catRepository.getCatById(ownedCat.catId)
                val abilities = abilityRepository.getCatAbilities(catId)

                _uiState.update {
                    it.copy(
                        selectedCat = OwnedCatDetailsData(
                            ownedCatData = ownedCat,
                            cat = cat,
                            abilities = abilities,
                            level = ownedCat.level,
                            experience = ownedCat.experience,
                            evolutionCat = null,
                            isElderOf = null
                        )
                    )
                }
            } catch (e: Exception){
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.FAILURE
                    )
                }
            }
        }
    }

    fun showPreviousCatsPage() {
        _uiState.update {
            it.copy(
                catListPage = it.catListPage - 1
            )
        }
        viewModelScope.launch {
            try{
                fetchCatPage()
            } catch (e: Exception){
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.FAILURE
                    )
                }
            }
        }
    }

    fun showNextCatsPage() {
        _uiState.update {
            it.copy(
               catListPage = it.catListPage + 1
            )
        }
        viewModelScope.launch {
            try{
                fetchCatPage()
            } catch (e: Exception){
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.FAILURE
                    )
                }
            }
        }
    }

    fun addSelectedCatToTeam(){
        if(!selectedCatIsInTeam()){
            val selectedTeam = _uiState.value.selectedTeam
            val teamList: MutableList<BasicCatData> = selectedTeam.cats.toMutableList()

            if(teamList.size < 4){
                val cat = _uiState.value.selectedCat
                teamList.add(
                    BasicCatData(
                        catId =  cat.ownedCatData.id,
                        image = cat.cat.image
                    )
                )
                val team = TeamData(
                    teamId = selectedTeam.teamId,
                    teamName = selectedTeam.teamName,
                    cats = teamList
                )
                _uiState.update{
                    it.copy(
                        selectedTeam = team
                    )
                }
            }
        }
    }

    private fun selectedCatIsInTeam(): Boolean {
        val selectedCat: OwnedCatDetailsData = _uiState.value.selectedCat
        val selectedTeam = _uiState.value.selectedTeam

        return selectedTeam.cats.find { selectedCat.ownedCatData.id == it.catId } != null
    }

    fun removeCatFromSelectedTeam(catId: Int){
        val selectedTeam = _uiState.value.selectedTeam
        val teamList: MutableList<BasicCatData> = selectedTeam.cats.toMutableList()

        teamList.removeIf { it.catId == catId }
        _uiState.update{
            it.copy(
                selectedTeam = TeamData(
                    teamId = selectedTeam.teamId,
                    teamName = selectedTeam.teamName,
                    cats = teamList
                )
            )
        }
    }

    fun UpdateTeamName(teamName: String) {
        _uiState.update {
            it.copy(
                selectedTeam = it.selectedTeam.copy(
                    teamName = teamName
                )
            )
        }
    }
}