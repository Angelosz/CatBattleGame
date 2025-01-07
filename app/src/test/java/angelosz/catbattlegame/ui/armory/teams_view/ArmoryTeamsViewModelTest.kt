package angelosz.catbattlegame.ui.armory.teams_view

import angelosz.catbattlegame.data.entities.EnemyDiscoveryState
import angelosz.catbattlegame.data.entities.OwnedCat
import angelosz.catbattlegame.data.entities.PlayerAccount
import angelosz.catbattlegame.data.entities.PlayerTeam
import angelosz.catbattlegame.data.entities.PlayerTeamOwnedCat
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.data.ArmoryTeam
import angelosz.catbattlegame.ui.armory.data.SimpleArmoryCatData
import angelosz.catbattlegame.ui.combat.BasicCatData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

lateinit var cats: List<SimpleArmoryCatData>
lateinit var armoryTeams: List<ArmoryTeam>

class ArmoryTeamsViewModelTest{
    private lateinit var viewModel: ArmoryTeamsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun onBefore() {
        Dispatchers.setMain(StandardTestDispatcher())

        viewModel = ArmoryTeamsViewModel(
            PlayerRepositoryForArmoryTeamsTest()
        )

        cats = listOf(
            SimpleArmoryCatData(
                id = 1,
                image = 1,
                1,
                20
            ),
            SimpleArmoryCatData(
                id = 2,
                image = 2,
                2,
                20
            ),
            SimpleArmoryCatData(
                id = 3,
                image = 3,
                2,
                20
            )
        )
        armoryTeams = listOf(
            ArmoryTeam(
                teamId = 1,
                teamName = "Team Name",
                cats = listOf(
                    cats[0],
                    cats[1]
                )
            ),
            ArmoryTeam(
                teamId = 2,
                teamName = "Team Name 2",
                cats = listOf(
                    cats[2]
                )
            )
        )


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `data is loaded correctly at setup`() = runTest {
        val uiState = ArmoryTeamsUiState(
            state = ScreenState.SUCCESS,
            teams = armoryTeams,
            selectedTeam = armoryTeams[0],
            isTeamSelected = false,
            cats = cats,
            pageLimit = 9,
            totalNumberOfCats = 3
        )

        viewModel.setup(9)
        testScheduler.advanceUntilIdle()

        assertEquals(uiState, viewModel.uiState.value)
    }

    @Test
    fun `add cat to team`() = runTest{
        viewModel.setup(9)
        testScheduler.advanceUntilIdle()

        val cat = cats[1]
        val expected = armoryTeams[1].cats.size + 1
        viewModel.selectTeam(armoryTeams[1].teamId)
        viewModel.addCatToTeam(cat)

        assertEquals(expected, viewModel.uiState.value.selectedTeam.cats.size)
    }

    @Test
    fun `same cat doesn't add cat to team`() = runTest{
        viewModel.setup(9)
        testScheduler.advanceUntilIdle()

        val cat = cats[2]
        val expected = armoryTeams[1].cats.size
        viewModel.selectTeam(armoryTeams[1].teamId)
        viewModel.addCatToTeam(cat)

        assertEquals(expected, viewModel.uiState.value.selectedTeam.cats.size)
    }

    @Test
    fun `remove cat to team`() = runTest{
        viewModel.setup(9)
        testScheduler.advanceUntilIdle()

        val cat = cats[2]
        val expected = armoryTeams[1].cats.size - 1
        viewModel.selectTeam(armoryTeams[1].teamId)
        viewModel.removeCatFromTeam(cat)

        assertEquals(expected, viewModel.uiState.value.selectedTeam.cats.size)
    }

    @Test
    fun `rename team`() = runTest {
        viewModel.setup(9)
        testScheduler.advanceUntilIdle()

        viewModel.renameTeam("New Name")

        assertEquals("New Name", viewModel.uiState.value.selectedTeam.teamName)
    }

    @Test
    fun `delete team`() = runTest {
        viewModel.setup(9)
        testScheduler.advanceUntilIdle()

        viewModel.deleteTeam(1)
        testScheduler.advanceUntilIdle()

        assertEquals(1, viewModel.uiState.value.teams.size)
        assertEquals(2, viewModel.uiState.value.teams[0].teamId)
    }
}

class PlayerRepositoryForArmoryTeamsTest: PlayerAccountRepository {
    override suspend fun getPlayerAccountAsFlow(): Flow<PlayerAccount?> {
        return flowOf<PlayerAccount?>(null)
    }
    override suspend fun getPlayerAccount(): PlayerAccount? = null
    override suspend fun insertPlayerAccount(playerAccount: PlayerAccount) {}
    override suspend fun updateAccount(playerAccount: PlayerAccount) {}

    override suspend fun addCrystals(amount: Int) {}
    override suspend fun reduceCrystals(amount: Int) {}
    override suspend fun addGold(amount: Int) {}
    override suspend fun reduceGold(amount: Int) {}
    override suspend fun insertOwnedCat(ownedCat: OwnedCat) {}
    override suspend fun insertOwnedCats(ownedCats: List<OwnedCat>) {}

    override suspend fun updateOwnedCat(ownedCat: OwnedCat) {}
    override suspend fun deleteOwnedCat(ownedCat: OwnedCat) {}
    override suspend fun getAllOwnedCats(): List<OwnedCat> = emptyList()
    override suspend fun getOwnedCatByCatId(catId: Int): OwnedCat = OwnedCat()
    override suspend fun getOwnedCatsByCatIds(catIds: List<Int>): List<OwnedCat> = emptyList()
    override suspend fun getPaginatedOwnedCats(limit: Int, offset: Int): List<OwnedCat> = emptyList()
    override suspend fun getOwnedCatsCount(): Int = 3
    override suspend fun ownsCat(catId: Int): Boolean = false
    override suspend fun getSimpleArmoryCatsDataPage(
        limit: Int,
        offset: Int,
    ): List<SimpleArmoryCatData> = cats

    override suspend fun getSimpleCatsDataFromTeam(teamId: Long): List<SimpleArmoryCatData> {
        val teams = armoryTeams
        for( team in teams){
            if(teamId == team.teamId) return team.cats
        }
        throw Exception()
    }
    override suspend fun insertPlayerTeam(playerTeam: PlayerTeam): Long = 0
    override suspend fun updatePlayerTeam(playerTeam: PlayerTeam) {}

    override suspend fun deleteTeam(playerTeam: PlayerTeam) {
        armoryTeams = armoryTeams.filterNot { it.teamId == playerTeam.id }
    }
    override suspend fun teamExists(teamId: Long): Boolean = false

    override suspend fun deleteTeamById(teamId: Long) {
        armoryTeams = armoryTeams.filterNot { it.teamId == teamId }
    }
    override suspend fun clearTeam(teamId: Long) {

    }
    override suspend fun getPlayerTeamById(teamId: Long): PlayerTeam = PlayerTeam()
    override suspend fun getAllPlayerTeams(): List<PlayerTeam> = armoryTeams.map{ team ->
        PlayerTeam(
            id = team.teamId,
            name = team.teamName
        )
    }
    override suspend fun addCatToTeam(playerTeamOwnedCat: PlayerTeamOwnedCat) {}
    override suspend fun getTeamData(teamId: Long): List<BasicCatData> = emptyList()
    override suspend fun discoverEnemies(enemies: List<EnemyDiscoveryState>) {}
}