package angelosz.catbattlegame.ui.combatreward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.BattleChestRepository
import angelosz.catbattlegame.data.repository.CampaignRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CampaignState
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.RewardType
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.data.entities.BattleChest
import angelosz.catbattlegame.data.entities.Campaign
import angelosz.catbattlegame.data.entities.CampaignChapter
import angelosz.catbattlegame.data.entities.ChapterReward
import angelosz.catbattlegame.domain.models.BasicCatData
import angelosz.catbattlegame.utils.GameConstants.EXPERIENCE_PER_LEVEL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CombatResultViewModel(
    private val campaignRepository: CampaignRepository,
    private val playerAccountRepository: PlayerAccountRepository,
    private val battleChestRepository: BattleChestRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<CombatResultUiState> = MutableStateFlow(CombatResultUiState())
    val uiState: StateFlow<CombatResultUiState> = _uiState

    fun setupCombatResult(teamId: Long, chapterId: Long, combatResult: CombatResult){
        _uiState.update {
            it.copy(
                screenState = ScreenState.LOADING,
                combatResult = combatResult,
            )
        }

        viewModelScope.launch {
            try {
                if(combatResult == CombatResult.PLAYER_WON){
                    val chapter = campaignRepository.getChapterById(chapterId)
                    val team = playerAccountRepository.getTeamData(teamId)

                    if(chapter.state != CampaignState.COMPLETED){
                        val chapterRewards = campaignRepository.getChapterRewards(chapterId)

                        addRewards(chapterRewards)
                        addCatsExperience(team, chapter.experience)

                        completeChapter(chapter)

                        if(chapter.isLastCampaignChapter){
                            val currentCampaign = campaignRepository.getCampaignById(chapter.campaignId)
                            completeCampaign(currentCampaign)
                            unlockNextCampaign(currentCampaign.nextCampaign)
                        } else {
                            unlockNextChapter(chapter.unlocksChapter)
                        }

                        _uiState.update {
                            it.copy(
                                chapter = chapter,
                                team = team,
                                chapterReward = chapterRewards,
                                experienceGained = chapter.experience,
                                screenState = ScreenState.SUCCESS
                            )
                        }
                    } else {
                        val experience = chapter.experience / 10
                        addCatsExperience(team, experience)

                        _uiState.update {
                            it.copy(
                                chapter = chapter,
                                team = team,
                                experienceGained = experience,
                                screenState = ScreenState.SUCCESS
                            )
                        }
                    }

                } else {
                    _uiState.update {
                        it.copy(
                            screenState = ScreenState.SUCCESS
                        )
                    }
                }
            } catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    screenState = ScreenState.FAILURE
                )
            }
        }
    }

    private suspend fun addCatsExperience(team: List<BasicCatData>, experience: Int) {
        team.forEach{ cat ->
            val ownedCat = playerAccountRepository.getOwnedCatById(cat.catId)
            val totalExperience = ownedCat.experience + experience

            if(totalExperience >= EXPERIENCE_PER_LEVEL){
                playerAccountRepository.updateOwnedCat(
                    ownedCat.copy(
                        experience = totalExperience - EXPERIENCE_PER_LEVEL,
                        level = ownedCat.level + 1
                    )
                )
            } else {
                playerAccountRepository.updateOwnedCat(
                    ownedCat.copy(
                        experience = totalExperience
                    )
                )
            }
        }
    }

    private suspend fun completeChapter(chapter: CampaignChapter) {
        campaignRepository.updateCampaignChapter(
            chapter.copy(
                state = CampaignState.COMPLETED
            )
        )
    }

    private suspend fun unlockNextChapter(unlocksChapter: Long) {
        val chapter = campaignRepository.getChapterById(unlocksChapter)
        campaignRepository.updateCampaignChapter(
            chapter.copy(
                state = CampaignState.UNLOCKED
            )
        )
    }

    private suspend fun completeCampaign(currentCampaign: Campaign) {
        campaignRepository.updateCampaign(
            currentCampaign.copy(
                state = CampaignState.COMPLETED
            )
        )
    }

    private suspend fun unlockNextCampaign(campaignId: Long) {
        val campaign = campaignRepository.getCampaignById(campaignId)
        campaignRepository.updateCampaign(
            campaign.copy(
                state = CampaignState.UNLOCKED
            )
        )
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
}
