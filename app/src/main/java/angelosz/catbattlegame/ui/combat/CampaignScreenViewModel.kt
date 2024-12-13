package angelosz.catbattlegame.ui.combat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.CampaignRepository
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.entities.Campaign
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CampaignScreenViewModel(private val campaignRepository: CampaignRepository): ViewModel() {
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
}