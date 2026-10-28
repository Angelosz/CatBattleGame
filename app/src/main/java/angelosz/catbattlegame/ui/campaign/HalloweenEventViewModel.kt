package angelosz.catbattlegame.ui.campaign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.datastore.DataStoreRepository
import angelosz.catbattlegame.data.repository.CampaignRepository
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.campaign.data.Chapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HalloweenChapterItem(
    val chapter: Chapter,
    val isDailyClaimed: Boolean
)

data class HalloweenEventUiState(
    val screenState: ScreenState = ScreenState.INITIALIZING,
    val chapters: List<HalloweenChapterItem> = emptyList()
)

class HalloweenEventViewModel(
    private val campaignRepository: CampaignRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HalloweenEventUiState())
    val uiState: StateFlow<HalloweenEventUiState> = _uiState

    fun setup() {
        _uiState.update { it.copy(screenState = ScreenState.LOADING) }
        viewModelScope.launch {
            try {
                val rawChapters = campaignRepository.getCampaignChaptersByCampaignId(1001L)
                val items = rawChapters.map { chapter ->
                    val claimed = dataStoreRepository.hasClaimedHalloweenDailyReward(chapter.id)
                    HalloweenChapterItem(chapter, claimed)
                }
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.SUCCESS,
                        chapters = items
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }
}
