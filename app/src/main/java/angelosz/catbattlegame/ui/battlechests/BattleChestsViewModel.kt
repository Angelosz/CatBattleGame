package angelosz.catbattlegame.ui.battlechests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.BattleChestRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.data.entities.BattleChest
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.data.entities.OwnedCat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BattleChestsViewModel(
    private val catRepository: CatRepository,
    private val playerAccountRepository: PlayerAccountRepository,
    private val battleChestRepository: BattleChestRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<BattleChestsUiState> =
        MutableStateFlow(BattleChestsUiState())
    val uiState: StateFlow<BattleChestsUiState> = _uiState

    init {
        setupInitialData()
    }

    fun setupInitialData() {
        _uiState.update {
            it.copy(
                screenState = ScreenState.LOADING,
            )
        }

        try{
            viewModelScope.launch {
                fetchBattleChests()

                _uiState.update {
                    it.copy(
                        screenState = ScreenState.SUCCESS,
                    )
                }
            }
        } catch (e: Exception){
            _uiState.update {
                it.copy(
                    screenState = ScreenState.FAILURE,
                )
            }
        }
    }

    private suspend fun fetchBattleChests() {
        val storedBattleChests = battleChestRepository.getAllBattleChests()
        val battleChests = mutableMapOf<Pair<CatRarity, BattleChestType>, List<BattleChest>>()

        //transform list to Map<Pair<CatRarity, BattleChestType>, Int>
        battleChests.putAll(storedBattleChests
            .groupBy { it.rarity to it.type }
        )

        // Get first battleChest if only 1 is available
        var selectedBattleChest: BattleChest? = null
        if(storedBattleChests.size == 1) {
            selectedBattleChest = storedBattleChests.first()
        }

        _uiState.update {
            it.copy(
                battleChests = battleChests,
                selectedBattleChest = selectedBattleChest,
                message = "You have ${storedBattleChests.size} package/s, click it to open it!"
            )
        }
    }

    fun openSelectedBattleChest(){
        try{
            viewModelScope.launch {
                val battleChest = uiState.value.selectedBattleChest
                when(battleChest?.type){
                    BattleChestType.NORMAL -> {
                        val cat = getRandomCatOfRarity(battleChest.rarity)

                        var message = "You got a ${cat.name}!"
                        if(!playerAccountRepository.ownsCat(cat.id)){
                            addCatToPlayerAccount(cat.id)
                        } else {
                            message = "You already have a ${cat.name}, you received ${disenchantCat(cat)} crystals instead!"
                        }

                        deleteBattleChest(battleChest)
                        _uiState.update {
                            it.copy(
                                catReward = cat,
                                message = message
                            )
                        }
                    }
                    BattleChestType.NEW_CAT -> {
                        val cat = getRandomUnownedCatOfRarity(battleChest.rarity)

                        var message = "You got a ${cat.name}!"
                        if(!playerAccountRepository.ownsCat(cat.id)){
                            addCatToPlayerAccount(cat.id)
                        } else {
                            message = "You already have a ${cat.name}, you received ${disenchantCat(cat)} crystals instead!"
                        }

                        deleteBattleChest(battleChest)
                        _uiState.update {
                            it.copy(
                                catReward = cat,
                                message = message
                            )
                        }
                    }
                    null -> { }
                }
            }
        } catch (e: Exception){
            _uiState.update {
                it.copy(
                    screenState = ScreenState.FAILURE,
                )
            }
        }
    }

    private suspend fun disenchantCat(cat: Cat): Int {
        val crystalValue = when(cat.rarity){
            CatRarity.KITTEN -> 20
            CatRarity.TEEN -> 40
            CatRarity.ADULT -> 80
            CatRarity.ELDER -> 160
        }
        playerAccountRepository.addCrystals(crystalValue)
        return crystalValue
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

    private suspend fun addCatToPlayerAccount(catId: Int){
        playerAccountRepository.insertOwnedCat(
            OwnedCat(
                catId = catId,
                level = 1,
                experience = 0
            )
        )
    }

    private suspend fun deleteBattleChest(battleChest: BattleChest){
        battleChestRepository.deleteBattleChest(battleChest)
    }


    fun countBattleChests(): Int {
        var count = 0
        for ( battleChest in _uiState.value.battleChests ){
            count += battleChest.value.size
        }
        return count
    }

    fun selectBattleChest(battleChest: BattleChest) {
        _uiState.update {
            it.copy(
                selectedBattleChest = battleChest
            )
        }
    }

    fun goBackToBattleChestsGrid(){
        try{
            viewModelScope.launch {
                fetchBattleChests()
                _uiState.update {
                    it.copy( catReward = null )
                }
            }
        } catch(e: Exception){
            _uiState.update {
                it.copy(
                    screenState = ScreenState.FAILURE,
                )
            }
        }
    }
}