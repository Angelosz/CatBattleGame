package angelosz.catbattlegame.ui.combatreward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.CampaignRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CombatResultViewModel(
    private val campaignRepository: CampaignRepository,
    private val playerAccountRepository: PlayerAccountRepository
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
                    val chapterRewards = campaignRepository.getChapterRewards(chapterId)

                    _uiState.update {
                        it.copy(
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
}
