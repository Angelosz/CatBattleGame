package angelosz.catbattlegame.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.database.initialdata.AbilitiesInitialData
import angelosz.catbattlegame.data.database.initialdata.CampaignsInitialData
import angelosz.catbattlegame.data.database.initialdata.CatsInitialData
import angelosz.catbattlegame.data.database.initialdata.ChaptersInitialData
import angelosz.catbattlegame.data.database.initialdata.EnemyCatsInitialData
import angelosz.catbattlegame.data.database.initialdata.PlayerInitialData
import angelosz.catbattlegame.data.entities.BattleChest
import angelosz.catbattlegame.data.entities.PlayerAccount
import angelosz.catbattlegame.data.entities.PlayerTeam
import angelosz.catbattlegame.data.entities.PlayerTeamOwnedCat
import angelosz.catbattlegame.data.entities.notifications.NotificationsEntity
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.BattleChestRepository
import angelosz.catbattlegame.data.repository.CampaignRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.EnemyCatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CampaignState
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.RewardType
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.home.notifications.HomeNotification
import angelosz.catbattlegame.ui.home.notifications.NotificationType
import angelosz.catbattlegame.utils.GameConstants
import angelosz.catbattlegame.utils.GameConstants.GET_BATTLECHEST_GOLD_COST
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val playerAccountRepository: PlayerAccountRepository,
    private val campaignRepository: CampaignRepository,
    private val catRepository: CatRepository,
    private val abilityRepository: AbilityRepository,
    private val enemyCatRepository: EnemyCatRepository,
    private val battleChestRepository: BattleChestRepository
    ): ViewModel() {

    private val _uiState: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun setup() {
        _uiState.update { it.copy(screenState = ScreenState.LOADING) }

        viewModelScope.launch {
            try {
                val playerAccount = initializePlayerAccount()

                val isDifferentVersion = playerAccount.gameVersion != GameConstants.GAME_VERSION
                if (isDifferentVersion) {
                    emptyInitialDataFromDatabase()
                    repopulateDatabase()

                    playerAccountRepository.updateAccount(
                        playerAccount.copy(
                            gameVersion = GameConstants.GAME_VERSION
                        )
                    )
                }


                val notifications = getConvertedNotifications(playerAccountRepository.getAllNotifications())

                _uiState.update {
                    it.copy(
                        gold = playerAccount.gold,
                        crystals = playerAccount.crystals,
                        notifications = notifications,
                        screenState = ScreenState.SUCCESS
                    )
                }

                viewModelScope.launch {
                    setupPlayerDataCollector()
                }
                viewModelScope.launch {
                    setupNotificationCollector()
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }

    }

    private suspend fun emptyInitialDataFromDatabase() {
        catRepository.clearCatsTable()
        enemyCatRepository.clearEnemyCatsTable()
        enemyCatRepository.clearChapterEnemiesTable()
        enemyCatRepository.clearEnemyAbilitiesTable()
        abilityRepository.clearAbilitiesTable()
        abilityRepository.clearAbilityCrossRefsTable()
        campaignRepository.clearCampaignsTable()
        campaignRepository.clearCampaignChaptersTable()
        campaignRepository.clearChapterRewardsTable()
    }

    private suspend fun setupNotificationCollector() {
        val notificationsEntitiesFlow = playerAccountRepository.getAllNotificationsAsFlow()
        notificationsEntitiesFlow.collect { notificationsEntities ->
            _uiState.update {
                it.copy(notifications = getConvertedNotifications(notificationsEntities))
            }
        }
    }

    private suspend fun getConvertedNotifications(
        notificationsEntities: List<NotificationsEntity>,
    ): MutableList<HomeNotification> {
        val notifications: MutableList<HomeNotification> = mutableListOf()
        notificationsEntities.forEach {
            val notification = when (it.notificationType) {
                NotificationType.LEVEL_UP -> playerAccountRepository.getLevelUpNotification(it.notificationId)
                NotificationType.CAT_EVOLUTION -> playerAccountRepository.getCatEvolutionNotification(it.notificationId)
                NotificationType.BATTLE_CHEST_REWARD -> playerAccountRepository.getBattleChestNotification(it.notificationId)
                NotificationType.CURRENCY_REWARD -> playerAccountRepository.getCurrencyNotification(it.notificationId)
            }
            notifications.add(notification)
        }
        return notifications
    }

    private suspend fun repopulateDatabase() {
        val abilityInitialData = AbilitiesInitialData()
        val enemyCatInitialData = EnemyCatsInitialData()
        val chaptersInitialData = ChaptersInitialData()

        catRepository.insertCats(CatsInitialData().cats)
        abilityRepository.insertAbilities(abilityInitialData.abilities)
        abilityRepository.insertCatAbilityCrossRefs(abilityInitialData.getCatAbilitiesCrossRef())
        enemyCatRepository.insertEnemyCats(enemyCatInitialData.enemyCats)
        enemyCatRepository.insertEnemyAbilities(enemyCatInitialData.enemyAbilities)
        enemyCatRepository.insertChapterEnemies(chaptersInitialData.chapterEnemies)
        campaignRepository.insertCampaigns(CampaignsInitialData().campaigns)
        campaignRepository.insertCampaignChapters(chaptersInitialData.campaignChapters)
        campaignRepository.insertChapterRewards(chaptersInitialData.chapterRewards)
    }

    private suspend fun initializePlayerAccount(): PlayerAccount {
        var playerAccount = playerAccountRepository.getPlayerAccount()
        if (playerAccount == null) {
            playerAccount = PlayerAccount(gameVersion = GameConstants.GAME_VERSION)
            val ownedCats = PlayerInitialData().ownedCats
            val teamId = 1L
            playerAccountRepository.insertPlayerAccount(playerAccount)

            playerAccountRepository.insertOwnedCats(ownedCats)
            playerAccountRepository.insertPlayerTeam(PlayerTeam(id = teamId, name = "Team 1"))
            ownedCats.forEachIndexed { index, ownedCat ->
                playerAccountRepository.addCatToTeam(
                    PlayerTeamOwnedCat(teamId.toInt(), ownedCat.catId, index)
                )
            }

            campaignRepository.updateCampaignState(1, CampaignState.UNLOCKED)
            campaignRepository.updateChapterState(1, CampaignState.UNLOCKED)
            repopulateDatabase()
        }

        return playerAccount
    }

    private suspend fun setupPlayerDataCollector() {
        val playerAccount = playerAccountRepository.getPlayerAccountAsFlow()
        playerAccount.collect { data ->
            if (data != null)
                _uiState.update {
                    it.copy(
                        gold = data.gold,
                        crystals = data.crystals
                    )
                }
        }
    }

    fun closeNotification() {
        viewModelScope.launch {
            try {
                val notification = _uiState.value.notifications.first()
                playerAccountRepository.deleteNotification(notification.id, notification.type)
            } catch (e: Exception){
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    fun addBattleChestToAccount(rarity: CatRarity, type: BattleChestType) {
        viewModelScope.launch {
            try {
                battleChestRepository.insertBattleChest(
                    BattleChest(
                        rarity = rarity,
                        type = type
                    )
                )
            } catch (e: Exception){
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    fun addCurrencyToAccount(amount: Int, type: RewardType) {
        viewModelScope.launch {
            try{
                when(type){
                    RewardType.GOLD ->{
                        playerAccountRepository.addGold(amount)
                    }
                    RewardType.CRYSTAL -> {
                        playerAccountRepository.addCrystals(amount)
                    }
                    else -> {}
                }
            } catch (e: Exception){
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    fun openShop() {
        _uiState.update {
            it.copy(
                shopIsOpen = true
            )
        }
    }

    fun closeShop() {
        _uiState.update {
            it.copy(
                shopIsOpen = false
            )
        }
    }

    fun battleChestWasBought(catRarity: CatRarity) {
        viewModelScope.launch {
            try{
                val cost = GET_BATTLECHEST_GOLD_COST(catRarity)
                val playerGold = playerAccountRepository.getGoldAmount()

                if(playerGold >= cost){
                    playerAccountRepository.reduceGold(cost)
                    addBattleChestToAccount(catRarity, BattleChestType.NORMAL)
                }
            } catch (e: Exception){
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }
}