package angelosz.catbattlegame.ui.campaign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.datastore.DataStoreRepository
import angelosz.catbattlegame.data.repository.CampaignRepository
import angelosz.catbattlegame.data.repository.EnemyCatRepository
import angelosz.catbattlegame.domain.enums.CampaignState
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.campaign.data.Campaign
import angelosz.catbattlegame.ui.campaign.data.Chapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CampaignScreenViewModel(
    private val campaignRepository: CampaignRepository,
    private val enemyCatRepository: EnemyCatRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CampaignScreenUiState())
    val uiState: StateFlow<CampaignScreenUiState> = _uiState

    fun setupInitialData() {
        _uiState.update { it.copy(screenState = ScreenState.LOADING) }
        try {
            viewModelScope.launch {
                val campaigns = campaignRepository.getAllCampaigns()
                val selectedCampaignId = dataStoreRepository.getSelectedCampaign()
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.SUCCESS,
                        campaigns = campaigns,
                        selectedCampaign = campaigns.find { campaign ->
                            campaign.id.toInt() == selectedCampaignId }
                            ?: Campaign(0),
                    )
                }
            }
        } catch (e: Exception){
            _uiState.update {
                it.copy(
                    screenState = ScreenState.FAILURE
                )
            }
        }
    }

    fun selectCampaign(campaign: Campaign) {
        _uiState.update {
            it.copy(
                screenState = ScreenState.LOADING,
            )
        }
        viewModelScope.launch{
            try {
                dataStoreRepository.saveSelectedCampaign(campaign.id.toInt())
                val campaignChapters = campaignRepository.getCampaignChaptersByCampaignId(campaign.id)

                _uiState.update {
                    it.copy(
                        screenState = ScreenState.SUCCESS,
                        selectedCampaign = campaign,
                        campaignChapters = campaignChapters,
                        stage = CampaignSelectionStage.SELECTING_CHAPTER
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

    fun selectCampaignChapter(chapter: Chapter) {
        if(chapter.state == CampaignState.LOCKED) return

        _uiState.update {
            it.copy(
                screenState = ScreenState.LOADING,
            )
        }
        viewModelScope.launch{
            try {
                val enemyCats = enemyCatRepository.getSimplifiedEnemiesByCampaignChapterId(chapter.id)
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.SUCCESS,
                        selectedCampaignChapter = chapter,
                        selectedCampaignChapterEnemyCats = enemyCats,
                        stage = CampaignSelectionStage.CHAPTER_SELECTED
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

    fun backToCampaignSelection() {
        _uiState.update {
            it.copy(
                stage = CampaignSelectionStage.SELECTING_CAMPAIGN
            )
        }
    }

    fun backToCampaignChapterSelection(){
        _uiState.update {
            it.copy(
                stage = CampaignSelectionStage.SELECTING_CHAPTER
            )
        }
    }
}