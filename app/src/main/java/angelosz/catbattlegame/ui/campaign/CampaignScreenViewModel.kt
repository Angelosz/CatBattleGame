package angelosz.catbattlegame.ui.campaign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.CampaignRepository
import angelosz.catbattlegame.data.repository.EnemyCatRepository
import angelosz.catbattlegame.domain.enums.CampaignState
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.entities.Campaign
import angelosz.catbattlegame.domain.models.entities.CampaignChapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CampaignScreenViewModel(
    private val campaignRepository: CampaignRepository,
    private val enemyCatRepository: EnemyCatRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CampaignScreenUiState())
    val uiState: StateFlow<CampaignScreenUiState> = _uiState

    init {
        setupInitialData()
    }

    fun setupInitialData() {
        try {
            viewModelScope.launch {
                val campaigns = campaignRepository.getAllCampaigns()
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.SUCCESS,
                        campaigns = campaigns
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

    fun selectCampaignChapter(campaignChapter: CampaignChapter) {
        if(campaignChapter.state == CampaignState.LOCKED) return

        _uiState.update {
            it.copy(
                screenState = ScreenState.LOADING,
            )
        }
        viewModelScope.launch{
            try {
                val enemyCats = enemyCatRepository.getSimplifiedEnemiesByCampaignChapterId(campaignChapter.id)
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.SUCCESS,
                        selectedCampaignChapter = campaignChapter,
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