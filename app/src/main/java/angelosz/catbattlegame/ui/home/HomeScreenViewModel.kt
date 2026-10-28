package angelosz.catbattlegame.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.database.initialdata.AbilitiesInitialData
import angelosz.catbattlegame.data.database.initialdata.CampaignsInitialData
import angelosz.catbattlegame.data.database.initialdata.CatsInitialData
import angelosz.catbattlegame.data.database.initialdata.ChaptersInitialData
import angelosz.catbattlegame.data.database.initialdata.EnemyCatsInitialData
import angelosz.catbattlegame.data.database.initialdata.PlayerInitialData
import angelosz.catbattlegame.data.datastore.DataStoreRepository
import angelosz.catbattlegame.data.datastore.PlayerSettings
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
    private val battleChestRepository: BattleChestRepository,
    private val dataStoreRepository: DataStoreRepository
    ): ViewModel() {

    private val _uiState: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun setup() {
        _uiState.update { it.copy(screenState = ScreenState.LOADING) }

        viewModelScope.launch {
            try {
                checkAndExecuteAlphaReset()
                val playerAccount = initializePlayerAccount()
                checkHalloweenEvent()

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

                ensureCampaign3UnlockedIfCampaign2Completed()
                ensureHalloweenCampaignUnlocked()


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
                viewModelScope.launch {
                    setupMailCollector()
                }
                checkDailyRewards()
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
        enemyCatRepository.insertEnemyAbilities(enemyCatInitialData.getEnemyAbilities())
        enemyCatRepository.insertChapterEnemies(chaptersInitialData.getChapterEnemies())
        campaignRepository.insertCampaigns(CampaignsInitialData().campaigns)
        campaignRepository.insertCampaignChapters(chaptersInitialData.campaignChapters)
        campaignRepository.insertChapterRewards(chaptersInitialData.getChapterRewards())
    }

    private suspend fun initializePlayerAccount(): PlayerAccount {
        var playerAccount = playerAccountRepository.getPlayerAccount()
        if (playerAccount == null) {
            dataStoreRepository.resetHalloweenEventClaim()
            dataStoreRepository.resetDailyRewardsData()
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
            campaignRepository.updateCampaignState(2, CampaignState.LOCKED)
            campaignRepository.updateCampaignState(3, CampaignState.LOCKED)
            campaignRepository.updateCampaignState(4, CampaignState.DEVELOPMENT)
            campaignRepository.updateChapterState(1, CampaignState.UNLOCKED)
            repopulateDatabase()
        }

        return playerAccount
    }

    private suspend fun ensureCampaign3UnlockedIfCampaign2Completed() {
        try {
            val campaign2State = campaignRepository.getCampaignState(2)
            val campaign3State = campaignRepository.getCampaignState(3)
            if (campaign2State == CampaignState.COMPLETED && campaign3State != CampaignState.UNLOCKED) {
                campaignRepository.updateCampaignState(3, CampaignState.UNLOCKED)
                campaignRepository.updateChapterState(16, CampaignState.UNLOCKED)
            } else if (campaign3State == CampaignState.DEVELOPMENT) {
                campaignRepository.updateCampaignState(3, CampaignState.LOCKED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun ensureHalloweenCampaignUnlocked() {
        try {
            val campaignState = campaignRepository.getCampaignState(1001L)
            if (campaignState != CampaignState.UNLOCKED) {
                campaignRepository.updateCampaignState(1001L, CampaignState.UNLOCKED)
            }
            val chapter28State = campaignRepository.getChapterState(28L)
            if (chapter28State == CampaignState.LOCKED) {
                campaignRepository.updateChapterState(28L, CampaignState.UNLOCKED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    fun openSettings() {
        viewModelScope.launch {
            try{
                val settings = dataStoreRepository.getSettings()

                _uiState.update {
                    it.copy(
                        playerSettings = settings,
                        shopIsOpen = false,
                        settingsIsOpen = true,
                    )
                }
            } catch (e: Exception){
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    fun saveSettings(settings: PlayerSettings) {
        viewModelScope.launch {
            try{
                dataStoreRepository.saveSettings(settings)
                _uiState.update { it.copy(settingsIsOpen = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }

    private suspend fun checkAndExecuteAlphaReset() {
        val hasReset = dataStoreRepository.hasCompletedAlphaResetV1()
        if (!hasReset) {
            playerAccountRepository.executeAlphaResetWithEventPreservation()
            dataStoreRepository.markAlphaResetV1Completed()
        }
    }

    private suspend fun checkHalloweenEvent() {
        val hasReceived = dataStoreRepository.hasReceivedHalloweenPackage()
        if (!hasReceived) {
            dataStoreRepository.markHalloweenPackageReceived()
            battleChestRepository.insertBattleChest(
                BattleChest(rarity = CatRarity.KITTEN, type = BattleChestType.HALLOWEEN_SPECIAL)
            )
            playerAccountRepository.insertMail(
                angelosz.catbattlegame.data.entities.MailEntity(
                    title = "It's Halloween!",
                    content = "A special Halloween package is waiting for you in your Armory! Open packages and meet Salem!",
                    rewardType = null,
                    rewardAmount = 0
                )
            )
        }
    }

    fun checkDailyRewards() {
        viewModelScope.launch {
            try {
                val todayStr = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    java.time.LocalDate.now().toString()
                } else {
                    java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                }
                val rewardState = dataStoreRepository.getDailyRewardState()
                val isAvailable = rewardState.lastClaimDate != todayStr
                var streak = rewardState.streak
                if (streak <= 0 || streak > 7) streak = 1

                _uiState.update {
                    it.copy(
                        isDailyRewardAvailable = isAvailable,
                        dailyRewardStreak = streak
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun claimDailyReward() {
        viewModelScope.launch {
            try {
                val todayStr = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    java.time.LocalDate.now().toString()
                } else {
                    java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                }
                val rewardState = dataStoreRepository.getDailyRewardState()
                if (rewardState.lastClaimDate == todayStr) return@launch

                var currentStreak = rewardState.streak
                if (currentStreak <= 0 || currentStreak >= 7) {
                    currentStreak = 1
                } else {
                    currentStreak += 1
                }

                when (currentStreak) {
                    1 -> playerAccountRepository.addGold(50)
                    2 -> playerAccountRepository.addGold(100)
                    3 -> battleChestRepository.insertBattleChest(BattleChest(rarity = CatRarity.KITTEN, type = BattleChestType.NORMAL))
                    4 -> playerAccountRepository.addGold(200)
                    5 -> playerAccountRepository.addCrystals(25)
                    6 -> battleChestRepository.insertBattleChest(BattleChest(rarity = CatRarity.TEEN, type = BattleChestType.NORMAL))
                    7 -> {
                        playerAccountRepository.addGold(400)
                        playerAccountRepository.addCrystals(50)
                    }
                }

                dataStoreRepository.recordDailyRewardClaim(todayStr, currentStreak)

                _uiState.update {
                    it.copy(
                        isDailyRewardAvailable = false,
                        dailyRewardStreak = currentStreak
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun setupMailCollector() {
        playerAccountRepository.getAllMailsAsFlow().collect { mailList ->
            _uiState.update {
                it.copy(
                    mails = mailList,
                    unreadMailCount = mailList.count { mail -> !mail.isRead }
                )
            }
        }
    }

    fun claimMailReward(mail: angelosz.catbattlegame.data.entities.MailEntity) {
        viewModelScope.launch {
            try {
                if (mail.isRewardClaimed) return@launch
                if (mail.rewardType != null && mail.rewardAmount > 0) {
                    when (mail.rewardType) {
                        angelosz.catbattlegame.domain.enums.RewardType.GOLD -> playerAccountRepository.addGold(mail.rewardAmount)
                        angelosz.catbattlegame.domain.enums.RewardType.CRYSTAL -> playerAccountRepository.addCrystals(mail.rewardAmount)
                        angelosz.catbattlegame.domain.enums.RewardType.KITTEN_BOX -> battleChestRepository.insertBattleChest(BattleChest(rarity = CatRarity.KITTEN, type = BattleChestType.NORMAL))
                        angelosz.catbattlegame.domain.enums.RewardType.TEEN_BOX -> battleChestRepository.insertBattleChest(BattleChest(rarity = CatRarity.TEEN, type = BattleChestType.NORMAL))
                        angelosz.catbattlegame.domain.enums.RewardType.ADULT_BOX -> battleChestRepository.insertBattleChest(BattleChest(rarity = CatRarity.ADULT, type = BattleChestType.NORMAL))
                        else -> {}
                    }
                }
                playerAccountRepository.updateMail(mail.copy(isRead = true, isRewardClaimed = true))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteMail(mail: angelosz.catbattlegame.data.entities.MailEntity) {
        viewModelScope.launch {
            try {
                playerAccountRepository.deleteMail(mail)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun openMailbox() {
        _uiState.update { it.copy(mailboxIsOpen = true) }
        viewModelScope.launch {
            try {
                _uiState.value.mails.filter { !it.isRead }.forEach { mail ->
                    playerAccountRepository.updateMail(mail.copy(isRead = true))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun closeMailbox() { _uiState.update { it.copy(mailboxIsOpen = false) } }
    fun openDailyReward() { _uiState.update { it.copy(dailyRewardIsOpen = true) } }
    fun closeDailyReward() { _uiState.update { it.copy(dailyRewardIsOpen = false) } }
}