package angelosz.catbattlegame.ui.combat.combatreward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.entities.BattleChest
import angelosz.catbattlegame.data.entities.ChapterReward
import angelosz.catbattlegame.data.entities.EnemyDiscoveryState
import angelosz.catbattlegame.data.entities.OwnedCat
import angelosz.catbattlegame.data.repository.BattleChestRepository
import angelosz.catbattlegame.data.repository.CampaignRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.EnemyCatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CampaignState
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.RewardType
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.campaign.data.Campaign
import angelosz.catbattlegame.ui.campaign.data.Chapter
import angelosz.catbattlegame.ui.combat.BasicCatData
import angelosz.catbattlegame.ui.home.notifications.CatEvolutionNotification
import angelosz.catbattlegame.ui.home.notifications.LevelUpNotification
import angelosz.catbattlegame.utils.GameConstants.ADULT_DISENCHANT_VALUE
import angelosz.catbattlegame.utils.GameConstants.ELDER_DISENCHANT_VALUE
import angelosz.catbattlegame.utils.GameConstants.EXPERIENCE_PER_LEVEL
import angelosz.catbattlegame.utils.GameConstants.KITTEN_DISENCHANT_VALUE
import angelosz.catbattlegame.utils.GameConstants.TEEN_DISENCHANT_VALUE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CombatResultViewModel(
    private val campaignRepository: CampaignRepository,
    private val playerAccountRepository: PlayerAccountRepository,
    private val battleChestRepository: BattleChestRepository,
    private val catRepository: CatRepository,
    private val enemyCatRepository: EnemyCatRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<CombatResultUiState> = MutableStateFlow(
        CombatResultUiState()
    )
    val uiState: StateFlow<CombatResultUiState> = _uiState

    private var rewardsProcessed = false

    fun setupCombatResult(teamId: Long, chapterId: Long, combatResult: CombatResult){
        _uiState.update {
            it.copy(
                screenState = ScreenState.LOADING,
                combatResult = combatResult,
            )
        }

        viewModelScope.launch {
            try {
                discoverEnemies(chapterId)
                val team = playerAccountRepository.getTeamData(teamId)
                val chapter = campaignRepository.getChapterById(chapterId)
                val chapterRewards = campaignRepository.getChapterRewards(chapterId)
                var activeRewards = emptyList<ChapterReward>()
                var expGained = 0

                if (!rewardsProcessed) {
                    rewardsProcessed = true
                    if(combatResult == CombatResult.PLAYER_WON){
                        if(chapter.state != CampaignState.COMPLETED){
                            addRewards(chapterRewards)
                            addCatsExperience(team, chapter.experience)
                            activeRewards = chapterRewards
                            expGained = chapter.experience

                            completeChapter(chapter)

                            if(chapter.isLastCampaignChapter){
                                val currentCampaign = campaignRepository.getCampaignById(chapter.campaignId)
                                completeCampaign(currentCampaign)
                                unlockNextCampaign(currentCampaign.nextCampaign)
                            }
                            unlockNextChapter(chapter.unlocksChapter)
                        } else {
                            val experience = chapter.experience / 4
                            addCatsExperience(team, experience)
                            activeRewards = addReplayRewards(chapterRewards)
                            expGained = experience
                        }
                    } else {
                        expGained = chapter.experience / 10
                        addCatsExperience(team, expGained)
                    }
                }

                _uiState.update {
                    it.copy(
                        chapter = chapter,
                        team = team,
                        chapterReward = activeRewards,
                        experienceGained = expGained,
                        screenState = ScreenState.SUCCESS
                    )
                }
            } catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    screenState = ScreenState.FAILURE
                )
            }
        }
    }

    private suspend fun discoverEnemies(chapterId: Long) {
        val enemies = enemyCatRepository.getSimplifiedEnemiesByCampaignChapterId(chapterId)

        playerAccountRepository.discoverEnemies(
            enemies.map{ enemy ->
                EnemyDiscoveryState(
                    enemyCatId = enemy.id,
                    state = true
                )
            }
        )
    }

    private suspend fun addCatsExperience(team: List<BasicCatData>, experience: Int) {
        if (experience <= 0) return
        team.forEach{ cat ->
            val ownedCat = playerAccountRepository.getOwnedCatByCatId(cat.catId)
            val baseCat = catRepository.getCatById(cat.catId)
            var currentXp = ownedCat.experience + experience
            var currentLevel = ownedCat.level
            var healthMod = ownedCat.healthModifier
            var attackMod = ownedCat.attackModifier
            var defenseMod = ownedCat.defenseModifier

            while (currentXp >= EXPERIENCE_PER_LEVEL && currentLevel < angelosz.catbattlegame.utils.GameConstants.MAX_CAT_LEVEL) {
                currentXp -= EXPERIENCE_PER_LEVEL
                currentLevel += 1
                healthMod += (baseCat.baseHealth * 0.1).toInt()
                attackMod += (baseCat.baseAttack * 0.1).toInt()
                defenseMod += (baseCat.baseDefense * 0.1).toInt()

                playerAccountRepository.insertLevelUpNotification(
                    LevelUpNotification(
                        name = baseCat.name,
                        image = baseCat.image,
                        level = currentLevel,
                        notificationText = ""
                    )
                )

                if(currentLevel >= baseCat.evolutionLevel){
                    val evolutionCatId = baseCat.nextEvolutionId
                    if(evolutionCatId != null){
                        val evolutionCat = catRepository.getCatById(evolutionCatId)
                        var message = ""
                        if(playerAccountRepository.ownsCat(evolutionCatId)){
                            val crystalValue = when(evolutionCat.rarity){
                                CatRarity.KITTEN -> KITTEN_DISENCHANT_VALUE
                                CatRarity.TEEN -> TEEN_DISENCHANT_VALUE
                                CatRarity.ADULT -> ADULT_DISENCHANT_VALUE
                                CatRarity.ELDER -> ELDER_DISENCHANT_VALUE
                            }
                            playerAccountRepository.addCrystals(crystalValue)
                            message = "is already in your armory, you gained $crystalValue crystals instead!"
                        } else {
                            playerAccountRepository.insertOwnedCat(
                                OwnedCat(
                                    catId = evolutionCatId,
                                    level = 1,
                                    experience = 0
                                )
                            )
                        }
                        playerAccountRepository.insertEvolutionNotification(
                            CatEvolutionNotification(
                                name = baseCat.name,
                                image = baseCat.image,
                                evolutionName = evolutionCat.name,
                                evolutionImage = evolutionCat.image,
                                notificationText = message
                            )
                        )
                    }
                }
            }

            if (currentLevel >= angelosz.catbattlegame.utils.GameConstants.MAX_CAT_LEVEL) {
                currentXp = 0
            }

            playerAccountRepository.updateOwnedCat(
                ownedCat.copy(
                    experience = currentXp,
                    level = currentLevel,
                    healthModifier = healthMod,
                    attackModifier = attackMod,
                    defenseModifier = defenseMod
                )
            )
        }
    }

    private suspend fun completeChapter(chapter: Chapter) {
        campaignRepository.updateChapterState(chapter.id, CampaignState.COMPLETED)
    }

    private suspend fun unlockNextChapter(chapterId: Long) {
        campaignRepository.updateChapterState(chapterId, CampaignState.UNLOCKED)
    }

    private suspend fun completeCampaign(currentCampaign: Campaign) {
        campaignRepository.updateCampaignState(currentCampaign.id, CampaignState.COMPLETED)
    }

    private suspend fun unlockNextCampaign(campaignId: Long) {
        campaignRepository.updateCampaignState(campaignId, CampaignState.UNLOCKED)
    }

    private suspend fun addRewards(chapterRewards: List<ChapterReward>) {
        chapterRewards.forEach { reward ->
            when(reward.rewardType){
                RewardType.GOLD -> { playerAccountRepository.addGold(reward.amount) }
                RewardType.CRYSTAL -> { playerAccountRepository.addCrystals(reward.amount) }
                RewardType.NEW_KITTEN -> { battleChestRepository.insertBattleChest(
                    BattleChest(
                        rarity = CatRarity.KITTEN,
                        type = BattleChestType.NEW_CAT
                    )
                ) }
                RewardType.KITTEN_BOX -> { battleChestRepository.insertBattleChest(
                    BattleChest(
                        rarity = CatRarity.KITTEN,
                        type = BattleChestType.NORMAL
                    )
                ) }
                RewardType.NEW_TEEN -> { battleChestRepository.insertBattleChest(
                    BattleChest(
                        rarity = CatRarity.TEEN,
                        type = BattleChestType.NEW_CAT
                    )
                ) }
                RewardType.TEEN_BOX -> { battleChestRepository.insertBattleChest(
                    BattleChest(
                        rarity = CatRarity.TEEN,
                        type = BattleChestType.NORMAL
                    )
                ) }
                RewardType.NEW_ADULT -> { battleChestRepository.insertBattleChest(
                    BattleChest(
                        rarity = CatRarity.ADULT,
                        type = BattleChestType.NEW_CAT
                    )
                ) }
                RewardType.ADULT_BOX -> { battleChestRepository.insertBattleChest(
                    BattleChest(
                        rarity = CatRarity.ADULT,
                        type = BattleChestType.NORMAL
                    )
                ) }
            }
        }
    }

    private suspend fun addReplayRewards(chapterRewards: List<ChapterReward>): List<ChapterReward> {
        val replayRewards = mutableListOf<ChapterReward>()
        chapterRewards.forEach { reward ->
            when(reward.rewardType){
                RewardType.GOLD -> {
                    val reducedGold = (reward.amount / 4).coerceAtLeast(1)
                    playerAccountRepository.addGold(reducedGold)
                    replayRewards.add(reward.copy(amount = reducedGold))
                }
                RewardType.CRYSTAL -> {
                    val reducedCrystals = (reward.amount / 4).coerceAtLeast(1)
                    playerAccountRepository.addCrystals(reducedCrystals)
                    replayRewards.add(reward.copy(amount = reducedCrystals))
                }
                // Boxes/Chests are NEVER awarded on replay
                else -> {}
            }
        }
        return replayRewards
    }

    fun rewardIncludesBattleChest(): Boolean {
        val battleChestRewards = listOf(
            RewardType.NEW_KITTEN,
            RewardType.KITTEN_BOX,
            RewardType.NEW_TEEN,
            RewardType.TEEN_BOX,
            RewardType.NEW_ADULT,
            RewardType.ADULT_BOX
        )
        for(reward in uiState.value.chapterReward){
            if(reward.rewardType in battleChestRewards) return true
        }
        return false
    }
}
