package angelosz.catbattlegame.ui.combatreward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.BattleChestRepository
import angelosz.catbattlegame.data.repository.CampaignRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.RewardType
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.entities.BattleChest
import angelosz.catbattlegame.domain.models.entities.ChapterReward
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
                    val chapterRewards = campaignRepository.getChapterRewards(chapterId)

                    addRewards(chapterRewards)
                    // addCatsExperience(chapter, team)

                    if(chapter.isLastCampaignChapter){
                        //unlockNextCampaign
                    } else {
                        //unlockNextChapter(chapter.unlocksChapter)
                    }

                    _uiState.update {
                        it.copy(
                            chapter = chapter,
                            team = team,
                            chapterReward = chapterRewards,
                            screenState = ScreenState.SUCCESS
                        )
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
