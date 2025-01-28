package angelosz.catbattlegame.ui.armory.cats_view

import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.data.entities.CatAbilityCrossRef
import angelosz.catbattlegame.data.entities.EnemyDiscoveryState
import angelosz.catbattlegame.data.entities.OwnedCat
import angelosz.catbattlegame.data.entities.PlayerAccount
import angelosz.catbattlegame.data.entities.PlayerTeam
import angelosz.catbattlegame.data.entities.PlayerTeamOwnedCat
import angelosz.catbattlegame.data.entities.notifications.NotificationsEntity
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.RewardType
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.archives.data.SimpleCatData
import angelosz.catbattlegame.ui.armory.data.DetailedArmoryCatData
import angelosz.catbattlegame.ui.armory.data.SimpleArmoryCatData
import angelosz.catbattlegame.ui.combat.BasicCatData
import angelosz.catbattlegame.ui.home.notifications.BattleChestNotification
import angelosz.catbattlegame.ui.home.notifications.CatEvolutionNotification
import angelosz.catbattlegame.ui.home.notifications.CurrencyRewardNotification
import angelosz.catbattlegame.ui.home.notifications.LevelUpNotification
import angelosz.catbattlegame.ui.home.notifications.NotificationType
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

val ownedCats: List<OwnedCat> = listOf(
    OwnedCat(
        id = 1,
        catId = 1,
        level = 1,
        experience = 20,
        healthModifier = 20,
        defenseModifier = 5,
        attackModifier = 5,
        attackSpeedModifier = 0.2f
    ),
    OwnedCat(2, catId = 2)
)
val cats: List<Cat> = listOf(
    Cat(
        id = 1,
        name = 1,
        baseHealth = 100f,
        baseAttack = 20f,
        baseDefense = 20f,
        attackSpeed = 1f,
    ),
    Cat(
        id = 2,
        name = 2,
        baseHealth = 100f,
        baseAttack = 20f,
        baseDefense = 20f,
        attackSpeed = 1f,
    ),
    Cat(id = 3, name = 3)
)
val simpleArmoryCatDataList = listOf(
    SimpleArmoryCatData(
        id = cats[0].id,
        image = cats[0].image,
        level = ownedCats[0].level,
        experience = ownedCats[0].experience,
    ),
    SimpleArmoryCatData(
        id = cats[1].id,
        image = cats[1].image,
        level = ownedCats[1].level,
        experience = ownedCats[1].experience,
    )
)
val firstCatDetailedData = DetailedArmoryCatData(
    id = cats[0].id,
    name = cats[0].name,
    image = cats[0].image,
    description = cats[0].description,
    armorType = cats[0].armorType,
    role = cats[0].role,
    health = cats[0].baseHealth + ownedCats[0].healthModifier,
    defense = cats[0].baseDefense + ownedCats[0].defenseModifier,
    attack = cats[0].baseAttack + ownedCats[0].attackModifier,
    speed = cats[0].attackSpeed + ownedCats[0].attackSpeedModifier,
    rarity = cats[0].rarity,
    abilities = emptyList(),
    level = ownedCats[0].level,
    experience = ownedCats[0].experience,
)
val secondDetailedCatData = DetailedArmoryCatData(
    id = cats[1].id,
    name = cats[1].name,
    image = cats[1].image,
    description = cats[1].description,
    armorType = cats[1].armorType,
    role = cats[1].role,
    health = cats[1].baseHealth + ownedCats[1].healthModifier,
    defense = cats[1].baseDefense + ownedCats[1].defenseModifier,
    attack = cats[1].baseAttack + ownedCats[1].attackModifier,
    speed = cats[1].attackSpeed + ownedCats[1].attackSpeedModifier,
    rarity = cats[1].rarity,
    abilities = emptyList(),
    level = ownedCats[1].level,
    experience = ownedCats[1].experience,
)

class ArmoryCatsViewModelTest {
    private lateinit var viewModel: ArmoryCatsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = ArmoryCatsViewModel(
            playerAccountRepository = PlayerRepositoryForArmoryTest(),
            catRepository = CatRepositoryForArmoryTest(),
            abilityRepository = AbilityRepositoryForArmoryTest()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `data is fetched correctly at setup`() = runTest {
        val uiState = ArmoryCatsUiState(
            state = ScreenState.SUCCESS,
            cats = simpleArmoryCatDataList,
            selectedCat = firstCatDetailedData,
            isDetailView = false,
            totalNumberOfCats = 2,
            page = 0,
            pageLimit = 9
        )

        viewModel.setup(pageLimit = 9)
        testScheduler.advanceUntilIdle()

        assertEquals(uiState, viewModel.uiState.value)
    }

    @Test
    fun selectCat() = runTest {
        val selectedCat = secondDetailedCatData

        viewModel.selectCat(2)
        testScheduler.advanceUntilIdle()

        assertEquals(selectedCat, viewModel.uiState.value.selectedCat)
    }

    @Test
    fun `trying to get owned cat with wrong id should set screenState to FAILURE`() = runTest {

        viewModel.selectCat(3)
        testScheduler.advanceUntilIdle()

        assertEquals(ScreenState.FAILURE, viewModel.uiState.value.state)
    }
}

class AbilityRepositoryForArmoryTest : AbilityRepository {
    override suspend fun getAllPlayerAbilities(): List<Ability> = emptyList()
    override suspend fun getAbilityById(id: Int): Ability = Ability(0)
    override suspend fun getCatAbilitiesPage(limit: Int, offset: Int): List<Ability> = emptyList()

    override suspend fun getCount(): Int = 0
    override suspend fun insertAbilities(abilities: List<Ability>) {}
    override suspend fun insertCatAbilityCrossRefs(catAbilityCrossRefs: List<CatAbilityCrossRef>) {}
    override suspend fun clearAbilitiesTable() {
    }

    override suspend fun clearAbilityCrossRefsTable() {
    }

    override suspend fun getCatAbilities(catId: Int): List<Ability> = emptyList()
    override suspend fun getEnemyCatAbilities(catId: Int): List<Ability> = emptyList()
}

class CatRepositoryForArmoryTest : CatRepository {
    override suspend fun getAllCats(): List<Cat>  = emptyList()
    override suspend fun getCatById(id: Int): Cat {
        for(cat in cats) if(cat.id == id) return cat
        throw Exception()
    }
    override suspend fun insertCats(cats: List<Cat>) {}
    override suspend fun clearCatsTable() {
    }

    override suspend fun getCatsById(ids: List<Int>): List<Cat>  = emptyList()
    override suspend fun getRandomCatByRarity(rarity: CatRarity): Cat = Cat()
    override suspend fun getUnownedCatsOfRarityIds(rarity: CatRarity): List<Int>  = emptyList()
    override suspend fun getSimpleCatDataFromPage(limit: Int, offset: Int): List<SimpleCatData> = emptyList()

    override suspend fun getCount(): Int = 0
}

class PlayerRepositoryForArmoryTest: PlayerAccountRepository {
    override suspend fun getPlayerAccountAsFlow(): Flow<PlayerAccount?> {
        return flowOf<PlayerAccount?>(null)
    }
    override suspend fun getPlayerAccount(): PlayerAccount? = null
    override suspend fun insertPlayerAccount(playerAccount: PlayerAccount) {}
    override suspend fun updateAccount(playerAccount: PlayerAccount) {}
    override suspend fun getCrystalsAmount(): Int = 0

    override suspend fun addCrystals(amount: Int) {}
    override suspend fun reduceCrystals(amount: Int) {}
    override suspend fun getGoldAmount(): Int = 0

    override suspend fun addGold(amount: Int) {}
    override suspend fun reduceGold(amount: Int) {}
    override suspend fun insertOwnedCat(ownedCat: OwnedCat) {}
    override suspend fun insertOwnedCats(ownedCats: List<OwnedCat>) {}

    override suspend fun updateOwnedCat(ownedCat: OwnedCat) {}
    override suspend fun deleteOwnedCat(ownedCat: OwnedCat) {}
    override suspend fun getAllOwnedCats(): List<OwnedCat> = emptyList()
    override suspend fun getOwnedCatByCatId(catId: Int): OwnedCat {
        for( cat in ownedCats) if(cat.catId == catId) return cat
        throw Exception()
    }
    override suspend fun getOwnedCatsByCatIds(catIds: List<Int>): List<OwnedCat> = emptyList()
    override suspend fun getPaginatedOwnedCats(limit: Int, offset: Int): List<OwnedCat> = emptyList()
    override suspend fun getOwnedCatsCount(): Int = ownedCats.size
    override suspend fun ownsCat(catId: Int): Boolean = false
    override suspend fun getSimpleArmoryCatsDataPage(
        limit: Int,
        offset: Int,
    ): List<SimpleArmoryCatData> = simpleArmoryCatDataList

    override suspend fun getSimpleCatsDataFromTeam(teamId: Long): List<SimpleArmoryCatData> = emptyList()

    override suspend fun insertPlayerTeam(playerTeam: PlayerTeam): Long = 0
    override suspend fun updatePlayerTeam(playerTeam: PlayerTeam) {}

    override suspend fun deleteTeam(playerTeam: PlayerTeam) {}
    override suspend fun teamExists(teamId: Long): Boolean = false

    override suspend fun deleteTeamById(teamId: Long) {}
    override suspend fun clearTeam(teamId: Long) {}
    override suspend fun getPlayerTeamById(teamId: Long): PlayerTeam = PlayerTeam()
    override suspend fun getAllPlayerTeams(): List<PlayerTeam> = emptyList()
    override suspend fun addCatToTeam(playerTeamOwnedCat: PlayerTeamOwnedCat) {}
    override suspend fun getTeamData(teamId: Long): List<BasicCatData> = emptyList()
    override suspend fun discoverEnemies(enemies: List<EnemyDiscoveryState>) {}
    override suspend fun getAllNotificationsAsFlow(): Flow<List<NotificationsEntity>> = flowOf(emptyList())

    override suspend fun getAllNotifications(): List<NotificationsEntity> = emptyList()

    override suspend fun deleteNotification(notificationId: Long, type: NotificationType) {}

    override suspend fun insertLevelUpNotification(notification: LevelUpNotification) {
    }

    override suspend fun insertEvolutionNotification(notification: CatEvolutionNotification) {
    }

    override suspend fun getLevelUpNotification(notificationId: Long): LevelUpNotification
    = LevelUpNotification(1, 1, 1, 1, "")

    override suspend fun getCatEvolutionNotification(notificationId: Long): CatEvolutionNotification
    = CatEvolutionNotification(1, 1, 1, 1, 1, "")

    override suspend fun insertBattleChestNotification(notification: BattleChestNotification) {
    }

    override suspend fun getBattleChestNotification(notificationId: Long): BattleChestNotification = BattleChestNotification(1, CatRarity.KITTEN,BattleChestType.NEW_CAT,"")

    override suspend fun insertCurrencyNotification(notification: CurrencyRewardNotification) {
    }

    override suspend fun getCurrencyNotification(notificationId: Long): CurrencyRewardNotification = CurrencyRewardNotification(0, 10, RewardType.GOLD, "")
}

