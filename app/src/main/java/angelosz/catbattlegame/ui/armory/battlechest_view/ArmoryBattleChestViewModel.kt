package angelosz.catbattlegame.ui.armory.battlechest_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.BattleChest
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.data.entities.OwnedCat
import angelosz.catbattlegame.data.repository.BattleChestRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.enums.ArmoryBattleChestStage
import angelosz.catbattlegame.utils.GameConstants.ADULT_DISENCHANT_VALUE
import angelosz.catbattlegame.utils.GameConstants.ELDER_DISENCHANT_VALUE
import angelosz.catbattlegame.utils.GameConstants.KITTEN_DISENCHANT_VALUE
import angelosz.catbattlegame.utils.GameConstants.TEEN_DISENCHANT_VALUE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArmoryBattleChestViewModel(
    private val catRepository: CatRepository,
    private val playerAccountRepository: PlayerAccountRepository,
    private val battleChestRepository: BattleChestRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ArmoryBattleChestUiState())
    val uiState: StateFlow<ArmoryBattleChestUiState> = _uiState

    private lateinit var onBattleChestOpened: () -> Unit

    fun setup(onBattleChestOpened: () -> Unit) {
        _uiState.update { it.copy(state = ScreenState.LOADING) }

        this.onBattleChestOpened = onBattleChestOpened

        viewModelScope.launch {
            try {

                loadBattleChests()

            } catch(e: Exception){
                _uiState.update { it.copy(state = ScreenState.FAILURE) }
            }
        }
    }

    fun reloadBattleChests(){
        viewModelScope.launch {
            try {
                loadBattleChests()
            } catch(e: Exception){
                _uiState.update { it.copy(state = ScreenState.FAILURE) }
            }
        }
    }

    private suspend fun loadBattleChests() {
        val battleChests = fetchBattleChests()

        if (thereIsOnlyOneIn(battleChests)) {
            val battleChest = battleChests.values.first().first()
            _uiState.update {
                it.copy(
                    state = ScreenState.SUCCESS,
                    battleChests = battleChests,
                    selectedBattleChest = battleChest,
                    armoryBattleChestView = ArmoryBattleChestStage.BATTLE_CHEST,
                    message = R.string.last_package
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    state = ScreenState.SUCCESS,
                    battleChests = battleChests,
                    armoryBattleChestView = ArmoryBattleChestStage.GRID
                )
            }
        }
    }

    private suspend fun fetchBattleChests(): Map<Pair<CatRarity, BattleChestType>, List<BattleChest>> {
        val storedBattleChests = battleChestRepository.getAllBattleChests()
        val battleChests = mutableMapOf<Pair<CatRarity, BattleChestType>, List<BattleChest>>()

        battleChests.putAll(storedBattleChests
            .groupBy { it.rarity to it.type }
        )

        return battleChests
    }

    fun openSelectedBattleChest(){
        viewModelScope.launch {
            try {
                val battleChest = _uiState.value.selectedBattleChest

                val cat = when (battleChest.type) {
                    BattleChestType.NEW_CAT -> getRandomUnownedCatOfRarity(battleChest.rarity)
                    BattleChestType.NORMAL -> getRandomCatOfRarity(battleChest.rarity)
                }

                addCatToPlayerAccount(cat)
                deleteBattleChest(battleChest)
                onBattleChestOpened()
            } catch (e: Exception) {
                _uiState.update { it.copy(state = ScreenState.FAILURE) }
            }
        }
    }

    fun selectBattleChest(battleChest: BattleChest) {
        _uiState.update {
            it.copy(
                selectedBattleChest = battleChest,
                armoryBattleChestView = ArmoryBattleChestStage.BATTLE_CHEST,
                message = R.string.open
            )
        }
    }

    private suspend fun deleteBattleChest(battleChest: BattleChest){
        battleChestRepository.deleteBattleChest(battleChest)
    }

    private suspend fun getRandomCatOfRarity(rarity: CatRarity): Cat {
        return catRepository.getRandomCatByRarity(rarity)
    }

    private suspend fun getRandomUnownedCatOfRarity(rarity: CatRarity): Cat {
        val unownedCatsIds = catRepository.getUnownedCatsOfRarityIds(rarity)
        return if(unownedCatsIds.isNotEmpty()){
            catRepository.getCatById(unownedCatsIds.random())
        } else {
            getRandomCatOfRarity(rarity)
        }
    }

    private suspend fun addCatToPlayerAccount(cat: Cat){
        var message = R.string.you_got_a_new_cat
        if(!playerAccountRepository.ownsCat(cat.id)){
            playerAccountRepository.insertOwnedCat(
                OwnedCat(
                    catId = cat.id,
                    level = 1,
                    experience = 0
                )
            )
        } else {
            message = R.string.it_seems_you_already_have_it_you_received_crystals_instead
        }
        _uiState.update {
            it.copy(
                catReward = cat,
                armoryBattleChestView = ArmoryBattleChestStage.CAT,
                message = message
            )
        }
    }

    private suspend fun disenchantCat(cat: Cat): Int {
        val crystalValue = when(cat.rarity){
            CatRarity.KITTEN -> KITTEN_DISENCHANT_VALUE
            CatRarity.TEEN -> TEEN_DISENCHANT_VALUE
            CatRarity.ADULT -> ADULT_DISENCHANT_VALUE
            CatRarity.ELDER -> ELDER_DISENCHANT_VALUE
        }
        playerAccountRepository.addCrystals(crystalValue)
        return crystalValue
    }

    private fun thereIsOnlyOneIn(battleChests: Map<Pair<CatRarity, BattleChestType>, List<BattleChest>>): Boolean {
        if(battleChests.size == 1){
            if(battleChests.values.first().size == 1){
                return true
            }
        }
        return false
    }

    fun returnToGrid(){
        _uiState.update {
            it.copy(
                armoryBattleChestView = ArmoryBattleChestStage.GRID
            )
        }
    }

    fun getView(): ArmoryBattleChestStage {
        return _uiState.value.armoryBattleChestView
    }
}